package me.puds.server.api.nbt.tag;

import io.netty.buffer.ByteBuf;
import me.puds.server.api.nbt.NbtTag;

public class LongTag extends NbtTag<Long> {
    @Override
    public int getTypeId() {
        return 4;
    }

    @Override
    public int getByteSize() {
        return 8;
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeLong(value);
    }

    @Override
    public void read(ByteBuf buffer) {
        value = buffer.readLong();
    }
}
