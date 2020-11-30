package me.puds.server.protocol.nbt.tag;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.puds.server.protocol.nbt.NbtTag;

public class CompoundTag extends NbtTag<ByteBuf> {
    @Override
    public int getTypeId() {
        return 10;
    }

    @Override
    public int getByteSize() {
        return value.readableBytes();
    }

    @Override
    public void write(ByteBuf buffer) {
        buffer.writeBytes(value);
    }

    @Override
    public void read(ByteBuf buffer) {
        value = Unpooled.buffer();
        while (buffer.isReadable()) {
            ByteBuf originalBuffer = buffer.copy();
            int size = 1;

            byte typeId = buffer.readByte();
            if (typeId != 0) {
                int nameLength = buffer.readUnsignedShort();
                size += 2 + nameLength;

                NbtTag<?> tag = NbtTag.createTagInstance(typeId);
                if (tag == null) {
                    return;
                }
                tag.read(buffer);

                size += tag.getByteSize();
            }

            originalBuffer.readBytes(value, size);
            if (typeId == 0) {
                break;
            }
        }
    }
}
