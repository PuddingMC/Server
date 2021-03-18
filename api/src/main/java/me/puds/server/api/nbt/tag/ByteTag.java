package me.puds.server.api.nbt.tag;

import io.netty.buffer.ByteBuf;
import me.puds.server.api.nbt.NbtTag;

public class ByteTag extends NbtTag<Byte> {
    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    public int getByteSize() {
        return 1;
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeByte(value);
    }

    @Override
    public void read(ByteBuf buffer) {
        value = buffer.readByte();
    }
}
