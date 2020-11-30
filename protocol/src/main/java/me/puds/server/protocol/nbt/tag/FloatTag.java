package me.puds.server.protocol.nbt.tag;

import io.netty.buffer.ByteBuf;
import me.puds.server.protocol.nbt.NbtTag;

public class FloatTag extends NbtTag<Float> {
    @Override
    public int getTypeId() {
        return 5;
    }

    @Override
    public int getByteSize() {
        return 4;
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeFloat(value);
    }

    @Override
    public void read(ByteBuf buffer) {
        value = buffer.readFloat();
    }
}
