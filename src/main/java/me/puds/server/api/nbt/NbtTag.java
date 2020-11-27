package me.puds.server.api.nbt;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.puds.server.api.nbt.tag.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class NbtTag<T> {
    // This list must be in order based on tag type ID
    private static final List<Class<?>> TAG_TYPES = List.of(
            EndTag.class,
            ByteTag.class,
            ShortTag.class,
            IntTag.class,
            LongTag.class,
            FloatTag.class,
            DoubleTag.class,
            ByteArrayTag.class,
            StringTag.class,
            ListTag.class,
            CompoundTag.class,
            IntArrayTag.class,
            LongArrayTag.class
    );

    protected String name;
    protected T value;

    public abstract int getTypeId();
    public abstract int getByteSize();

    public abstract void write(ByteBuf buffer);
    public abstract void read(ByteBuf buffer);

    public static NbtTag<?> createTagInstance(int typeId) {
        if (typeId + 1 > TAG_TYPES.size()) {
            return null;
        }

        Class<?> clazz = TAG_TYPES.get(typeId);
        Object instance;
        try {
            instance = clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }

        if (instance instanceof NbtTag) {
            return (NbtTag<?>) instance;
        }
        return null;
    }

    public static int getTagId(Class<?> type) {
        return TAG_TYPES.indexOf(type);
    }
}
