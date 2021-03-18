package me.puds.server.api.protocol.packet.client;

import me.puds.server.api.protocol.*;

public class PlayerPositionRotationPacket extends Packet {
    private double x;
    private double footY;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;

    public PlayerPositionRotationPacket(double x, double footY, double z, float yaw, float pitch, boolean onGround) {
        this.x = x;
        this.footY = footY;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public PlayerPositionRotationPacket() {
        this.x = 0;
        this.footY = 0;
        this.z = 0;
        this.yaw = 0;
        this.pitch = 0;
        this.onGround = true;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x13;
    }

    @Override
    public ConnectionState getState() {
        return ConnectionState.PLAY;
    }

    @Override
    public PacketSender getSender() {
        return PacketSender.CLIENT;
    }

    @Override
    public void fromBuffer(ProtocolVersion version, PacketBuffer buffer) {
        x = buffer.readDouble();
        footY = buffer.readDouble();
        z = buffer.readDouble();
        yaw = buffer.readFloat();
        pitch = buffer.readFloat();
        onGround = buffer.readBoolean();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeDouble(x);
        buffer.writeDouble(footY);
        buffer.writeDouble(z);
        buffer.writeFloat(yaw);
        buffer.writeFloat(pitch);
        buffer.writeBoolean(onGround);
        return buffer;
    }

    public double getX() {
        return x;
    }

    public double getFootY() {
        return footY;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setFootY(double footY) {
        this.footY = footY;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
    
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
