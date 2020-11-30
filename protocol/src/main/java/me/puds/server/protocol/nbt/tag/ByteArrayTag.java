package me.puds.server.protocol.nbt.tag;

import io.netty.buffer.ByteBuf;
import me.puds.server.protocol.nbt.NbtTag;

public class ByteArrayTag extends NbtTag<ByteBuf> {
    @Override
    public int getTypeId() {
        return 7;
    }

    @Override
    public int getByteSize() {
        return 4 + value.readableBytes();
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeInt(getByteSize());
        buffer.writeBytes(value);
    }

    @Override
    public void read(ByteBuf buffer) {
        int length = buffer.readInt();
        value = buffer.readBytes(length);
    }
}
