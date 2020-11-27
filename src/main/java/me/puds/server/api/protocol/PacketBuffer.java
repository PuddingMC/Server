package me.puds.server.api.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.RequiredArgsConstructor;
import me.puds.server.api.text.TextComponent;
import org.json.JSONObject;

import java.util.UUID;

@RequiredArgsConstructor
public class PacketBuffer {
    private final ByteBuf buffer;

    public PacketBuffer() {
        this.buffer = Unpooled.buffer();
    }

    public PacketBuffer(PacketBuffer buffer) {
        this.buffer = buffer.buffer.copy();
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

    public void writeBoolean(boolean value) {
        buffer.writeBoolean(value);
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
        writeVarInt(value.getBytes().length);
        writeBytes(value.getBytes());
    }

    public void writeChat(TextComponent value) {
        writeJson(value.toJson());
    }

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

    public void writePosition(Vector3 value, boolean legacy) {
        long x = value.getX() & 0x3FFFFFF;
        long y = value.getY() & 0xFFF;
        long z = value.getZ() & 0x3FFFFFF;

        long encoded;
        if (legacy) { // Positions were encoded differently before 1.14
            encoded = (x << 38) | (y << 26) | z;
        } else {
            encoded = (x << 38) | (z << 12) | y;
        }

        buffer.writeLong(encoded);
    }

    public void writePosition(Vector3 value, ProtocolVersion version) {
        boolean legacy = !version.isNewerThan(ProtocolVersion.RELEASE_1_16);
        writePosition(value, legacy);
    }

    public void writePosition(Vector3 value) {
        writePosition(value, false);
    }

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

    public boolean readBoolean() {
        return buffer.readBoolean();
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

    public TextComponent readChat() {
        return new TextComponent(readJson());
    }

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

    public Vector3 readPosition(boolean legacy) {
        long encoded = readLong();

        int x, y, z;
        if (legacy) { // Positions were encoded differently before 1.14
            x = (int) (encoded >> 38);
            y = (int) ((encoded >> 26) & 0xFFF);
            z = (int) (encoded << 38 >> 38);
        } else {
            x = (int) (encoded >> 38);
            y = (int) (encoded & 0xFFF);
            z = (int) (encoded << 26 >> 38);
        }

        return new Vector3(x, y, z);
    }

    public Vector3 readPosition(ProtocolVersion version) {
        boolean legacy = !version.isNewerThan(ProtocolVersion.RELEASE_1_16);
        return readPosition(legacy);
    }

    public Vector3 readPosition() {
        return readPosition(false);
    }

    // TODO: writeAngle

    public UUID readUuid() {
        return new UUID(readLong(), readLong());
    }

    public JSONObject readJson() {
        return new JSONObject(readString());
    }
}
