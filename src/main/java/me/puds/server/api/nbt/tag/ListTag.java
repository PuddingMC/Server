package me.puds.server.api.nbt.tag;

import io.netty.buffer.ByteBuf;
import me.puds.server.api.Server;
import me.puds.server.api.nbt.NbtTag;

import java.util.ArrayList;
import java.util.List;

public class ListTag<T extends NbtTag<?>> extends NbtTag<List<T>> {
    private int elementSize = 0;

    @Override
    public int getTypeId() {
        return 9;
    }

    @Override
    public int getByteSize() {
        return 4 + (value.size() * elementSize);
    }

    @Override
    public void write(ByteBuf buffer) {
        int typeId = 0;
        if (!value.isEmpty()) {
            typeId = value.get(0).getTypeId();
        }

        buffer.writeByte(typeId);
        buffer.writeInt(value.size());
        for (T element : value) {
            element.write(buffer);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void read(ByteBuf buffer) {
        byte typeId = buffer.readByte();
        NbtTag<?> tagType = NbtTag.createTagInstance(typeId);
        if (tagType == null) {
            Server.getLogger().warn("Invalid NBT list type " + typeId + ".");
            return;
        }

        List<T> value = new ArrayList<>();

        int length = buffer.readInt();
        elementSize = tagType.getByteSize();
        for (int i = 0; i < length; i++) {
            ByteBuf tagBuffer = buffer.readBytes(elementSize);
            // TODO: Find some way to check this cast
            NbtTag<T> instance = (NbtTag<T>) NbtTag.createTagInstance(typeId);
            if (instance == null) { // It is definitely not null but this will stop IDEA from complaining
                return;
            }

            instance.read(tagBuffer);
            value.add(instance.getValue());
        }

        this.value = value;
    }
}
