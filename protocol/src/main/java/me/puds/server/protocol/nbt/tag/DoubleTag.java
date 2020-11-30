package me.puds.server.protocol.nbt.tag;

import io.netty.buffer.ByteBuf;
import me.puds.server.protocol.nbt.NbtTag;

public class DoubleTag extends NbtTag<Double> {
    @Override
    public int getTypeId() {
        return 6;
    }

    @Override
    public int getByteSize() {
        return 8;
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeDouble(value);
    }

    @Override
    public void read(ByteBuf buffer) {
        value = buffer.readDouble();
    }
}
