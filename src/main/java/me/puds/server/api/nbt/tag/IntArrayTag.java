package me.puds.server.api.nbt.tag;

import io.netty.buffer.ByteBuf;
import me.puds.server.api.nbt.NbtTag;

import java.util.ArrayList;
import java.util.List;

public class IntArrayTag extends NbtTag<List<Integer>> {
    @Override
    public int getTypeId() {
        return 11;
    }

    @Override
    public int getByteSize() {
        return 4 + (value.size() * 4);
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeInt(value.size());
        for (int element : value) {
            buffer.writeInt(element);
        }
    }

    @Override
    public void read(ByteBuf buffer) {
        value = new ArrayList<>();
        int length = buffer.readInt();
        for (int i = 0; i < length; i++) {
            value.add(buffer.readInt());
        }
    }
}
