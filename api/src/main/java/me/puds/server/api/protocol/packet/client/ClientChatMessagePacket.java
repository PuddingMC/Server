package me.puds.server.api.protocol.packet.client;

import me.puds.server.api.protocol.*;

public class ClientChatMessagePacket extends Packet {
    private String message;

    public ClientChatMessagePacket(String message) {
        this.message = message;
    }

    public ClientChatMessagePacket() {
        this.message = "";
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            return 0x03;
        } else {
            return 0x02;
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
        this.message = buffer.readString();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeString(message);
        return buffer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
