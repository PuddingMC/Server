package me.puds.server.protocol.nbt;

import io.netty.buffer.ByteBuf;
import me.puds.server.protocol.nbt.tag.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class NbtCompound {
    private final ByteBuf buffer;
    private final String name;
    private final Map<String, NbtTag<?>> data = new HashMap<>();

    public NbtCompound(ByteBuf buffer) {
        buffer.readByte();

        this.buffer = buffer;
        name = readString();

        while (buffer.isReadable()) {
            int typeId = buffer.readByte();
            if (typeId == 0) {
                break;
            }

            String tagName = readString();
            NbtTag<?> instance = NbtTag.createTagInstance(typeId);
            if (instance == null) {
                // TODO: Find some way to log this
                return;
            }
            instance.read(buffer);

            data.put(tagName, instance);
        }
    }

    public NbtCompound(CompoundTag tag) {
        this(tag.getValue());
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
}
