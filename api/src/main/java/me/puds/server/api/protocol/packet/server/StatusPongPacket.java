package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;

public class StatusPongPacket extends Packet {
    private long payload;

    public StatusPongPacket(long payload) {
        this.payload = payload;
    }

    public StatusPongPacket() {
        payload = 0;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x01;
    }

    @Override
    public ConnectionState getState() {
        return ConnectionState.STATUS;
    }

    @Override
    public PacketSender getSender() {
        return PacketSender.SERVER;
    }

    @Override
    public void fromBuffer(ProtocolVersion version, PacketBuffer buffer) {
        payload = buffer.readLong();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeLong(payload);
        return buffer;
    }

    public long getPayload() {
        return payload;
    }

    public void setPayload(long payload) {
        this.payload = payload;
    }
}
