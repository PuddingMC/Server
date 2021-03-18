package me.puds.server.api.protocol.packet.client;

import me.puds.server.api.protocol.*;

public class PlayerMovementPacket extends Packet {
    private boolean onGround;

    public PlayerMovementPacket(boolean onGround) {
        this.onGround = onGround;
    }

    public PlayerMovementPacket() {
        this.onGround = true;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x15;
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
        this.onGround = buffer.readBoolean();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeBoolean(onGround);
        return buffer;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
