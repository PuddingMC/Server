package me.puds.server.protocol.packet.client;

import me.puds.server.protocol.*;

public class StatusRequestPacket extends Packet {
    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x00;
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
        // This packet has no fields
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        return new PacketBuffer();
    }
}
