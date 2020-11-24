package me.puds.server.api.nbt.tag;

import io.netty.buffer.ByteBuf;
import me.puds.server.api.nbt.NbtTag;

public class EndTag extends NbtTag<Void> {
    @Override
    public int getTypeId() {
        return 0;
    }

    @Override
    public int getByteSize() {
        return 0;
    }

    @Override
    public void write(ByteBuf buffer) {
    }

    @Override
    public void read(ByteBuf buffer) {
    }
}
