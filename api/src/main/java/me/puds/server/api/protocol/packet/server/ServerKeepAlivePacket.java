package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;

public class ServerKeepAlivePacket extends Packet {
    private long keepAliveId;

    public ServerKeepAlivePacket(long keepAliveId) {
        this.keepAliveId = keepAliveId;
    }

    public ServerKeepAlivePacket() {
        keepAliveId = 0;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x1f;
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
        keepAliveId = buffer.readLong();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeLong(keepAliveId);
        return buffer;
    }

    public long getKeepAliveId() {
        return keepAliveId;
    }

    public void setKeepAliveId(long keepAliveId) {
        this.keepAliveId = keepAliveId;
    }
}
