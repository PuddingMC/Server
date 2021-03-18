package me.puds.server.api.protocol.packet.client;

import me.puds.server.api.protocol.*;

public class PlayerPositionPacket extends Packet {
    private double x;
    private double feetY;
    private double z;
    private boolean onGround;

    public PlayerPositionPacket(double x, double feetY, double z, boolean onGround) {
        this.x = x;
        this.feetY = feetY;
        this.z = z;
        this.onGround = onGround;
    }

    public PlayerPositionPacket() {
        this.x = 0;
        this.feetY = 0;
        this.z = 0;
        this.onGround = true;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x12;
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
        feetY = buffer.readDouble();
        z = buffer.readDouble();
        onGround = buffer.readBoolean();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeDouble(x);
        buffer.writeDouble(feetY);
        buffer.writeDouble(z);
        buffer.writeBoolean(onGround);
        return buffer;
    }

    public double getX() {
        return x;
    }

    public double getFeetY() {
        return feetY;
    }

    public double getZ() {
        return z;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setFeetY(double feetY) {
        this.feetY = feetY;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
