package me.puds.server.api.nbt.tag;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.puds.server.api.nbt.NbtTag;

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
        System.out.println("Reading CompoundTag");
        while (buffer.isReadable()) {
            ByteBuf originalBuffer = buffer.copy();
            int size = 1;

            byte typeId = buffer.readByte();
            System.out.println("Next type ID is " + typeId);

            if (typeId != 0) {
                int nameLength = buffer.readUnsignedShort();
                System.out.println("Length of tag name is " + nameLength);
                size += 2 + nameLength;

                ByteBuf testBuf = buffer.copy();
                byte[] bytes = new byte[nameLength];
                testBuf.readBytes(bytes);
                System.out.println("Tag name is " + new String(bytes));

                NbtTag<?> tag = NbtTag.createTagInstance(typeId);
                if (tag == null) {
                    System.out.println("Tag instance is null - invalid type ID?");
                    System.out.println("Done reading CompoundTag");
                    return;
                }
                System.out.println("Created tag instance of " + tag.getClass().getName());
                tag.read(buffer);
                System.out.println("Value of tag is " + tag.getValue());

                size += tag.getByteSize();
            }

            originalBuffer.readBytes(value, size);
            if (typeId == 0) {
                System.out.println("Reached end tag - breaking out of loop");
                break;
            }
        }
        System.out.println("Done reading CompoundTag");
    }
}
