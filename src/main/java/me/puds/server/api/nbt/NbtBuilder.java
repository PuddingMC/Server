package me.puds.server.api.nbt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.RequiredArgsConstructor;
import me.puds.server.api.nbt.tag.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class NbtBuilder {
    private final ByteBuf buffer;

    private NbtBuilder() {
        this.buffer = Unpooled.buffer();
    }

    public NbtBuilder compound(String name) {
        return add(name, null, new CompoundTag());
    }

    public NbtBuilder end() {
        return add(null, null, new EndTag());
    }

    public NbtBuilder addByte(String name, byte value) {
        return add(name, value, new ByteTag());
    }

    public NbtBuilder addShort(String name, short value) {
        return add(name, value, new ShortTag());
    }

    public NbtBuilder addInt(String name, int value) {
        return add(name, value, new IntTag());
    }

    public NbtBuilder addLong(String name, long value) {
        return add(name, value, new LongTag());
    }

    public NbtBuilder addFloat(String name, float value) {
        return add(name, value, new FloatTag());
    }

    public NbtBuilder addDouble(String name, double value) {
        return add(name, value, new DoubleTag());
    }

    public NbtBuilder addByteArray(String name, ByteBuf value) {
        return add(name, value, new ByteArrayTag());
    }

    public NbtBuilder addString(String name, String value) {
        return add(name, value, new StringTag());
    }

    public NbtBuilder addCompound(String name, NbtCompound value) {
        return add(name, value.getBuffer(), new CompoundTag());
    }

    public NbtBuilder addIntArray(String name, List<Integer> value) {
        return add(name, value, new IntArrayTag());
    }

    public NbtBuilder addLongArray(String name, List<Long> value) {
        return add(name, value, new LongArrayTag());
    }

    public NbtBuilder addByteList(String name, List<Byte> values) {
        return addList(name, values, new ByteTag());
    }

    public NbtBuilder addShortList(String name, List<Short> values) {
        return addList(name, values, new ShortTag());
    }

    public NbtBuilder addIntList(String name, List<Integer> values) {
        return addList(name, values, new IntTag());
    }

    public NbtBuilder addLongList(String name, List<Long> values) {
        return addList(name, values, new LongTag());
    }

    public NbtBuilder addFloatList(String name, List<Float> values) {
        return addList(name, values, new FloatTag());
    }

    public NbtBuilder addDoubleList(String name, List<Double> values) {
        return addList(name, values, new DoubleTag());
    }

    public NbtBuilder addByteArrayList(String name, List<ByteBuf> values) {
        return addList(name, values, new ByteArrayTag());
    }

    public NbtBuilder addStringList(String name, List<String> values) {
        return addList(name, values, new StringTag());
    }

    public NbtBuilder addCompoundList(String name, List<NbtCompound> values) {
        List<ByteBuf> bufferList = values.stream()
                .map(NbtCompound::getBuffer)
                .collect(Collectors.toList());
        return addList(name, bufferList, new CompoundTag());
    }

    public NbtBuilder addIntArraylist(String name, List<List<Integer>> values) {
        return addList(name, values, new IntArrayTag());
    }

    public NbtBuilder addLongArrayList(String name, List<List<Long>> values) {
        return addList(name, values, new LongArrayTag());
    }

    public NbtCompound build() {
        return new NbtCompound(buffer);
    }

    public static NbtBuilder builder(ByteBuf buffer) {
        return new NbtBuilder(buffer);
    }

    public static NbtBuilder builder() {
        return new NbtBuilder();
    }

    private <T> NbtBuilder add(String name, T value, NbtTag<T> instance) {
        buffer.writeByte(instance.getTypeId());
        if (name != null) {
            buffer.writeShort(name.getBytes().length);
            buffer.writeBytes(name.getBytes());
        }
        if (value != null) {
            instance.setValue(value);
        }
        instance.write(buffer);
        return this;
    }

    @SuppressWarnings("unchecked")
    private <T, U extends NbtTag<T>> NbtBuilder addList(String name, List<T> values, U instance) {
        List<U> list = new ArrayList<>();
        for (T value : values) {
            U tag = (U) NbtTag.createTagInstance(instance.getTypeId());
            if (tag == null) { // This will never be null but it stops IDEA from complaining
                continue;
            }
            tag.setValue(value);
            list.add(tag);
        }
        return add(name, list, new ListTag<>());
    }
}
