package me.puds.server.api.protocol.client;

import me.puds.server.api.protocol.*;

public class StatusPingPacket extends Packet {
    private long payload;

    public StatusPingPacket() {
        payload = 0;
    }

    public StatusPingPacket(long payload) {
        this.payload = payload;
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
        return PacketSender.CLIENT;
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
