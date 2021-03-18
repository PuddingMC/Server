package me.puds.server.api.nbt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.puds.server.api.nbt.tag.*;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NbtCompound {
    private final ByteBuf buffer;
    private String name;
    private Map<String, NbtTag<?>> data;

    public NbtCompound(ByteBuf buffer) {
        this.buffer = buffer;
        load();
    }

    public NbtCompound(CompoundTag tag) {
        this(tag.getValue());
    }

    public NbtCompound(File file) {
        FileInputStream fileStream = null;
        try {
            fileStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        buffer = Unpooled.buffer();
        if (fileStream != null) {
            try (GZIPInputStream gzipStream = new GZIPInputStream(fileStream)) {
                buffer.writeBytes(gzipStream.readAllBytes());
                load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeToFile(File file) {
        FileOutputStream fileStream = null;
        GZIPOutputStream gzipStream = null;
        try {
            fileStream = new FileOutputStream(file);
            gzipStream = new GZIPOutputStream(fileStream);

            buffer.readBytes(gzipStream, buffer.readableBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
                if (gzipStream != null) {
                    gzipStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean contains(String name) {
        return data.containsKey(name);
    }

    public Set<String> keySet() {
        return data.keySet();
    }

    public NbtCompound getCompound(String name) {
        CompoundTag tag = (CompoundTag) data.get(name);
        return new NbtCompound(tag.getValue());
    }

    public byte getByte(String name) {
        ByteTag tag = (ByteTag) data.get(name);
        return tag.getValue();
    }

    public short getShort(String name) {
        ShortTag tag = (ShortTag) data.get(name);
        return tag.getValue();
    }

    public int getInt(String name) {
        IntTag tag = (IntTag) data.get(name);
        return tag.getValue();
    }

    public long getLong(String name) {
        LongTag tag = (LongTag) data.get(name);
        return tag.getValue();
    }

    public float getFloat(String name) {
        FloatTag tag = (FloatTag) data.get(name);
        return tag.getValue();
    }

    public double getDouble(String name) {
        DoubleTag tag = (DoubleTag) data.get(name);
        return tag.getValue();
    }

    public ByteBuf getByteArray(String name) {
        ByteArrayTag tag = (ByteArrayTag) data.get(name);
        return tag.getValue();
    }

    public String getString(String name) {
        StringTag tag = (StringTag) data.get(name);
        return tag.getValue();
    }

    public List<Integer> getIntArray(String name) {
        IntArrayTag tag = (IntArrayTag) data.get(name);
        return tag.getValue();
    }

    public List<Long> getLongArray(String name) {
        LongArrayTag tag = (LongArrayTag) data.get(name);
        return tag.getValue();
    }

    public List<NbtCompound> getCompoundList(String name) {
        return this.<CompoundTag, NbtCompound>constructList(name, NbtCompound::new);
    }

    public List<Byte> getByteList(String name) {
        return constructList(name, ByteTag::getValue);
    }

    public List<Short> getShortList(String name) {
        return constructList(name, ShortTag::getValue);
    }

    public List<Integer> getIntList(String name) {
        return constructList(name, IntTag::getValue);
    }

    public List<Long> getLongList(String name) {
        return constructList(name, LongTag::getValue);
    }

    public List<Float> getFloatList(String name) {
        return constructList(name, FloatTag::getValue);
    }

    public List<Double> getDoubleList(String name) {
        return constructList(name, DoubleTag::getValue);
    }

    public List<ByteBuf> getByteArrayList(String name) {
        return constructList(name, ByteArrayTag::getValue);
    }

    public List<String> getStringList(String name) {
        return constructList(name, StringTag::getValue);
    }

    public List<List<Integer>> getIntArrayList(String name) {
        return constructList(name, IntArrayTag::getValue);
    }

    public List<List<Long>> getLongArrayList(String name) {
        return constructList(name, LongArrayTag::getValue);
    }

    public NbtBuilder getBuilder() {
        return NbtBuilder.builder(buffer);
    }

    private String readString() {
        int length = buffer.readUnsignedShort();
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        return new String(bytes);
    }

    private void load() {
        System.out.println("Disregarding first byte (presumably 0a hex / 10 dec)");
        System.out.println("First byte is " + buffer.readByte());
        name = readString();
        System.out.println("Compound name is " + name);

        data = new HashMap<>();
        while (buffer.isReadable()) {
            int typeId = buffer.readByte();
            System.out.println("Next type ID is " + typeId);
            if (typeId == 0) {
                System.out.println("Reached end tag - breaking out of loop");
                break;
            }

            String tagName = readString();
            System.out.println("Tag name is " + tagName);
            NbtTag<?> instance = NbtTag.createTagInstance(typeId);
            if (instance == null) {
                System.out.println("Tag instance is null - invalid type ID?");
                // TODO: Find some way to log this
                return;
            }
            System.out.println("Created tag instance of " + instance.getClass().getName());
            instance.read(buffer);

            data.put(tagName, instance);
            System.out.println("Moving onto next tag");
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends NbtTag<?>, U> List<U> constructList(String name, Function<T, U> function) {
        ListTag<T> tag = (ListTag<T>) data.get(name);
        List<U> result = new ArrayList<>();
        for (T element : tag.getValue()) {
            result.add(function.apply(element));
        }
        return result;
    }

    public ByteBuf getBuffer() {
        return buffer;
    }

    public String getName() {
        return name;
    }

    public Map<String, NbtTag<?>> getData() {
        return data;
    }
}
