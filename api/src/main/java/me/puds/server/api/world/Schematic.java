package me.puds.server.api.world;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.puds.server.api.NamespacedKey;
import me.puds.server.api.Server;
import me.puds.server.api.nbt.NbtBuilder;
import me.puds.server.api.nbt.NbtCompound;
import me.puds.server.api.protocol.Vector;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Schematic {
    public final int LATEST_FORMAT_VERSION = 2;
    public final int LATEST_DATA_VERSION = 2685; // 20w49a

    private File file = null;

    private int formatVersion; // The version of the Sponge schematic specification
    private int dataVersion;

    private String name = null;
    private String author = null;
    private LocalDateTime creationDate = LocalDateTime.now();
    private List<String> requiredMods = new ArrayList<>();

    private short width;
    private short height;
    private short length;

    private Vector offset = new Vector();

    private List<Block> blocks = new ArrayList<>();
    // TODO: Entities?

    public void load(File file) {
        this.file = file;

        NbtCompound nbt = new NbtCompound(file);
        formatVersion = nbt.getInt("Version");
        dataVersion = nbt.getInt("DataVersion");

        if (nbt.contains("Metadata")) {
            NbtCompound metadata = nbt.getCompound("Metadata");
            if (metadata.contains("Name")) {
                name = metadata.getString("Name");
            }
            if (metadata.contains("Author")) {
                author = metadata.getString("Author");
            }
            if (metadata.contains("Date")) {
                long timestamp = metadata.getLong("Date");
                creationDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                        TimeZone.getDefault().toZoneId());
            }
            if (metadata.contains("RequiredMods")) {
                requiredMods = metadata.getStringList("RequiredMods");
            }
        }

        width = nbt.getShort("Width");
        height = nbt.getShort("Height");
        length = nbt.getShort("Length");

        if (nbt.contains("Offset")) {
            List<Integer> offsetArray = nbt.getIntArray("Offset");
            int offsetX = 0;
            int offsetY = 0;
            int offsetZ = 0;
            if (offsetArray.size() >= 1) {
                offsetX = offsetArray.get(0);
            }
            if (offsetArray.size() >= 2) {
                offsetY = offsetArray.get(1);
            }
            if (offsetArray.size() >= 3) {
                offsetZ = offsetArray.get(2);
            }
            offset = new Vector(offsetX, offsetY, offsetZ);
        }

        Biome[][] biomes = new Biome[length][width];
        if (nbt.contains("BiomeData")) {
            Map<Integer, Biome> biomePalette = new HashMap<>();
            if (nbt.contains("BiomePalette")) {
                NbtCompound biomePaletteNbt = nbt.getCompound("BiomePalette");
                for (String key : biomePaletteNbt.keySet()) {
                    NamespacedKey identifier = new NamespacedKey(key);
                    Biome biome = Biome.fromIdentifier(identifier);

                    int index = biomePaletteNbt.getInt(key);
                    biomePalette.put(index, biome);
                }
            } else {
                Server.getLogger().warn("The schematic" + file.getName() + " doesn't contain a biome palette. " +
                        "This could cause compatibility issues with future Minecraft versions.");

                for (Biome biome : Biome.values()) {
                    biomePalette.put(biome.ordinal(), biome);
                }
            }

            ByteBuf biomeBuffer = nbt.getByteArray("BiomeData");
            for (short x = 0; x < width; x++) {
                for (short z = 0; z < length; z++) {
                    int biomeId = readVarInt(biomeBuffer);
                    Biome biome = biomePalette.get(biomeId);
                    biomes[x][z] = biome;
                }
            }
        }

        Map<Integer, Material> blockPalette = new HashMap<>();
        if (nbt.contains("Palette")) {
            NbtCompound paletteNbt = nbt.getCompound("Palette");
            for (String key : paletteNbt.keySet()) {
                NamespacedKey identifier = new NamespacedKey(key);
                Material material = Material.fromIdentifier(identifier);

                int index = paletteNbt.getInt(key);
                blockPalette.put(index, material);
            }
        } else {
            Server.getLogger().warn("The schematic " + file.getName() + " doesn't contain a block palette. " +
                    "This WILL cause compatibility issues with other servers and future Minecraft versions.");

            for (Material material : Material.values()) {
                blockPalette.put(material.ordinal(), material);
            }
        }

        ByteBuf blockBuffer = nbt.getByteArray("BlockData");
        for (short x = 0; x < width; x++) {
            for (short y = 0; y < height; y++) {
                for (short z = 0; z < length; z++) {
                    int materialId = readVarInt(blockBuffer);
                    Material material = blockPalette.get(materialId);

                    Location location = new Location(this, x, y, z);
                    Biome biome = nbt.contains("BiomeData") ? biomes[x][z] : Biome.PLAINS;
                    Block block = new Block(location, material, biome);

                    blocks.add(block);
                }
            }
        }

        // TODO: Block entities
        // TODO: Entities
    }

    public void save(File file) {
        this.file = file;

        NbtBuilder builder = NbtBuilder.builder()
                .compound("Schematic")
                .addInt("Version", formatVersion)
                .addInt("DataVersion", dataVersion)
                .compound("Metadata")
                .addString("Name", name)
                .addString("Author", author)
                .addLong("Date", creationDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .addStringList("RequiredMods", requiredMods)
                .end()
                .addShort("Width", width)
                .addShort("Height", height)
                .addShort("Length", length)
                .addIntArray("Offset", List.of(offset.getX(), offset.getY(), offset.getZ()));

        Map<String, Integer> palette = new HashMap<>();
        Map<String, Integer> biomePalette = new HashMap<>();
        List<Integer> blockData = new ArrayList<>();
        List<Integer> biomeData = new ArrayList<>();

        List<Double> currentBiomeX = new ArrayList<>();
        List<Double> currentBiomeZ = new ArrayList<>();
        for (Block block : blocks) {
            String materialString = block.getMaterial().toIdentifier().toString();
            if (!palette.containsKey(materialString)) {
                palette.put(materialString, palette.size());
            }

            int materialId = palette.get(materialString);
            blockData.add(materialId);

            if (!currentBiomeX.contains(block.getLocation().getX()) ||
                    !currentBiomeZ.contains(block.getLocation().getZ())) {
                currentBiomeX.add(block.getLocation().getX());
                currentBiomeZ.add(block.getLocation().getZ());

                String biomeString = block.getBiome().toIdentifier().toString();
                if (!biomePalette.containsKey(biomeString)) {
                    biomePalette.put(biomeString, biomePalette.size());
                }

                int biomeId = biomePalette.get(biomeString);
                biomeData.add(biomeId);
            }
        }

        NbtBuilder paletteBuilder = NbtBuilder.builder();
        for (Map.Entry<String, Integer> entry : palette.entrySet()) {
            paletteBuilder.addInt(entry.getKey(), entry.getValue());
        }

        NbtBuilder biomePaletteBuilder = NbtBuilder.builder();
        for (Map.Entry<String, Integer> entry : biomePalette.entrySet()) {
            biomePaletteBuilder.addInt(entry.getKey(), entry.getValue());
        }

        NbtCompound nbt = builder
                .addCompound("Palette", paletteBuilder.build())
                .addIntArray("BlockData", blockData)
                .addCompound("BiomePalette", biomePaletteBuilder.build())
                .addIntArray("BiomeData", biomeData)
                .end()
                .build();
        nbt.writeToFile(file);
    }

    public void save() {
        save(file);
    }

    private int readVarInt(ByteBuf buffer) {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = buffer.readByte();
            int value = read & 0b01111111;
            result |= value << (7 * numRead);

            numRead++;
        } while ((read & 0b10000000) != 0);

        return result;
    }

    private ByteBuf writeVarInt(int value) {
        ByteBuf buffer = Unpooled.buffer();
        do {
            byte temp = (byte) (value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            buffer.writeByte(temp);
        } while (value != 0);
        return buffer;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(int formatVersion) {
        this.formatVersion = formatVersion;
    }

    public int getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(int dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<String> getRequiredMods() {
        return requiredMods;
    }

    public void setRequiredMods(List<String> requiredMods) {
        this.requiredMods = requiredMods;
    }

    public short getWidth() {
        return width;
    }

    public void setWidth(short width) {
        this.width = width;
    }

    public short getHeight() {
        return height;
    }

    public void setHeight(short height) {
        this.height = height;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public Vector getOffset() {
        return offset;
    }

    public void setOffset(Vector offset) {
        this.offset = offset;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }
}
