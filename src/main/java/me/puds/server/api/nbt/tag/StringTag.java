package me.puds.server.api.nbt.tag;

import io.netty.buffer.ByteBuf;
import me.puds.server.api.nbt.NbtTag;

public class StringTag extends NbtTag<String> {
    @Override
    public int getTypeId() {
        return 8;
    }

    @Override
    public int getByteSize() {
        return 2 + value.getBytes().length;
    }

    @Override
    public void write(ByteBuf buffer) {
        byte[] bytes = value.getBytes();
        buffer.writeShort(bytes.length);
        buffer.writeBytes(bytes);
    }

    @Override
    public void read(ByteBuf buffer) {
        int length = buffer.readUnsignedShort();
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        value = new String(bytes);
    }
}
