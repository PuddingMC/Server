package me.puds.server.api.protocol.packet.client;

import me.puds.server.api.protocol.*;

public class ClientKeepAlivePacket extends Packet {
    private long keepAliveId;

    public ClientKeepAlivePacket(long keepAliveId) {
        this.keepAliveId = keepAliveId;
    }

    public ClientKeepAlivePacket() {
        keepAliveId = 0;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            return 0x10;
        } else {
            return 0x0b;
        }
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
