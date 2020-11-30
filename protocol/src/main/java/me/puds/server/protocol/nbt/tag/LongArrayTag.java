package me.puds.server.protocol.nbt.tag;

import io.netty.buffer.ByteBuf;
import me.puds.server.protocol.nbt.NbtTag;

import java.util.ArrayList;
import java.util.List;

public class LongArrayTag extends NbtTag<List<Long>> {
    @Override
    public int getTypeId() {
        return 12;
    }

    @Override
    public int getByteSize() {
        return 4 + (value.size() * 8);
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeInt(value.size());
        for (long element : value) {
            buffer.writeLong(element);
        }
    }

    @Override
    public void read(ByteBuf buffer) {
        value = new ArrayList<>();
        int length = buffer.readInt();
        for (int i = 0; i < length; i++) {
            value.add(buffer.readLong());
        }
    }
}
