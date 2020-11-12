package me.puds.server.api.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.json.JSONObject;

import java.util.UUID;

public class PacketBuffer {
    private final ByteBuf buffer;

    public PacketBuffer() {
        this.buffer = Unpooled.buffer();
    }

    public PacketBuffer(ByteBuf buffer) {
        this.buffer = buffer;
    }

    public byte[] toByteArray() {
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.getBytes(buffer.readerIndex(), bytes);
        return bytes;
    }

    public int getSize() {
        return buffer.readableBytes();
    }

    public void writeByte(int value) {
        buffer.writeByte(value);
    }

    public void writeBytes(byte[] values) {
        buffer.writeBytes(values);
    }

    public void writeShort(int value) {
        buffer.writeShort(value);
    }

    public void writeInt(int value) {
        buffer.writeInt(value);
    }

    public void writeLong(long value) {
        buffer.writeLong(value);
    }

    public void writeFloat(float value) {
        buffer.writeFloat(value);
    }

    public void writeDouble(double value) {
        buffer.writeDouble(value);
    }

    public void writeString(String value) {
        writeVarInt(value.length());
        writeBytes(value.getBytes());
    }

    // TODO: writeChat
    // TODO: writeIdentifier

    public void writeVarInt(int value) {
        do {
            byte temp = (byte) (value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            writeByte(temp);
        } while (value != 0);
    }

    public void writeVarLong(long value) {
        do {
            byte temp = (byte) (value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            writeByte(temp);
        } while (value != 0);
    }

    // TODO: writeEntityMetadata
    // TODO: writeSlot
    // TODO: writeNbtTag
    // TODO: writePosition
    // TODO: writeAngle

    public void writeUuid(UUID value) {
        writeLong(value.getMostSignificantBits());
        writeLong(value.getLeastSignificantBits());
    }

    public void writeJson(JSONObject value) {
        writeString(value.toString());
    }

    public byte readByte() {
        return buffer.readByte();
    }

    public short readUnsignedByte() {
        return buffer.readUnsignedByte();
    }

    public byte[] readBytes(int count) {
        byte[] bytes = new byte[count];
        buffer.readBytes(bytes);
        return bytes;
    }

    public short readShort() {
        return buffer.readShort();
    }

    public int readUnsignedShort() {
        return buffer.readUnsignedShort();
    }

    public int readInt() {
        return buffer.readInt();
    }

    public long readLong() {
        return buffer.readLong();
    }

    public float readFloat() {
        return buffer.readFloat();
    }

    public double readDouble() {
        return buffer.readDouble();
    }

    public String readString() {
        byte[] bytes = readBytes(readVarInt());
        return new String(bytes);
    }

    // TODO: readChat
    // TODO: readIdentifier

    public int readVarInt() {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = readByte();
            int value = read & 0b01111111;
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                // TODO: Handle this
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public long readVarLong() {
        int numRead = 0;
        long result = 0;
        byte read;
        do {
            read = readByte();
            long value = read & 0b01111111;
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 10) {
                // TODO: Handle this
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    // TODO: writeEntityMetadata
    // TODO: writeSlot
    // TODO: writeNbtTag
    // TODO: writePosition
    // TODO: writeAngle

    public UUID readUuid() {
        return new UUID(readLong(), readLong());
    }

    public JSONObject readJson() {
        return new JSONObject(readString());
    }
}
