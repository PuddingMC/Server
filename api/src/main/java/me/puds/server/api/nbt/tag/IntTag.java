package me.puds.server.api.nbt.tag;

import io.netty.buffer.ByteBuf;
import me.puds.server.api.nbt.NbtTag;

public class IntTag extends NbtTag<Integer> {
    @Override
    public int getTypeId() {
        return 3;
    }

    @Override
    public int getByteSize() {
        return 4;
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeInt(value);
    }

    @Override
    public void read(ByteBuf buffer) {
        value = buffer.readInt();
    }
}
