package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;

import java.util.List;

public class ExplosionPacket extends Packet {
    private float x;
    private float y;
    private float z;
    private float radius;
    private int recordCount;
    private List<Vector> records;
    private float playerMotionX;
    private float playerMotionY;
    private float playerMotionZ;

    public ExplosionPacket(float x, float y, float z, float radius, int recordCount, List<Vector> records,
                           float playerMotionX, float playerMotionY, float playerMotionZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.recordCount = recordCount;
        this.records = records;
        this.playerMotionX = playerMotionX;
        this.playerMotionY = playerMotionY;
        this.playerMotionZ = playerMotionZ;
    }

    public ExplosionPacket() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.radius = 0;
        this.recordCount = 0;
        this.playerMotionX = 0;
        this.playerMotionY = 0;
        this.playerMotionZ = 0;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            return 0x1b;
        } else {
            return 0x1c;
        }
    }

    @Override
    public ConnectionState getState() {
        return ConnectionState.PLAY;
    }

    @Override
    public PacketSender getSender() {
        return PacketSender.SERVER;
    }

    @Override
    public void fromBuffer(ProtocolVersion version, PacketBuffer buffer) {
        x = buffer.readFloat();
        y = buffer.readFloat();
        z = buffer.readFloat();
        radius = buffer.readFloat();
        recordCount = buffer.readInt();
        for (int i = 0; i < recordCount; i++) {
            int x = buffer.readByte();
            int y = buffer.readByte();
            int z = buffer.readByte();
            Vector vector = new Vector(x, y, z);
            records.add(vector);
        }
        playerMotionX = buffer.readFloat();
        playerMotionY = buffer.readFloat();
        playerMotionZ = buffer.readFloat();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeFloat(x);
        buffer.writeFloat(y);
        buffer.writeFloat(z);
        buffer.writeFloat(radius);
        buffer.writeInt(recordCount);
        for (Vector vector : records) {
            buffer.writeByte(vector.getX());
            buffer.writeByte(vector.getY());
            buffer.writeByte(vector.getZ());
        }
        buffer.writeFloat(playerMotionX);
        buffer.writeFloat(playerMotionY);
        buffer.writeFloat(playerMotionZ);
        return buffer;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getRadius() {
        return radius;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public List<Vector> getRecords() {
        return records;
    }

    public float getPlayerMotionX() {
        return playerMotionX;
    }

    public float getPlayerMotionY() {
        return playerMotionY;
    }

    public float getPlayerMotionZ() {
        return playerMotionZ;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public void setRecords(List<Vector> records) {
        this.records = records;
    }

    public void setPlayerMotionX(float playerMotionX) {
        this.playerMotionX = playerMotionX;
    }

    public void setPlayerMotionY(float playerMotionY) {
        this.playerMotionY = playerMotionY;
    }

    public void setPlayerMotionZ(float playerMotionZ) {
        this.playerMotionZ = playerMotionZ;
    }
}
