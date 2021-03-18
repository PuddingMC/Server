package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;

public class StatusResponsePacket extends Packet {
    private PingResponse response;

    public StatusResponsePacket(PingResponse response) {
        this.response = response;
    }

    public StatusResponsePacket() {
        response = new PingResponse();
    }

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
        return PacketSender.SERVER;
    }

    @Override
    public void fromBuffer(ProtocolVersion version, PacketBuffer buffer) {
        response = new PingResponse(buffer.readJson());
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeJson(response.toJson());
        return buffer;
    }

    public PingResponse getResponse() {
        return response;
    }

    public void setResponse(PingResponse response) {
        this.response = response;
    }
}
