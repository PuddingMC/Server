package me.puds.server.api.nbt.tag;

import io.netty.buffer.ByteBuf;
import me.puds.server.api.nbt.NbtTag;

public class ShortTag extends NbtTag<Short> {
    @Override
    public int getTypeId() {
        return 2;
    }

    @Override
    public int getByteSize() {
        return 2;
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeShort(value);
    }

    @Override
    public void read(ByteBuf buffer) {
        value = buffer.readShort();
    }
}
