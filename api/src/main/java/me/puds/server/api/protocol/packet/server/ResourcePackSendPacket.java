package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;

public class ResourcePackSendPacket extends Packet {
    private String url;
    private String hash;

    public ResourcePackSendPacket(String url, String hash) {
        this.url = url;
        this.hash = hash;
    }

    public ResourcePackSendPacket() {
        this.url = "";
        this.url = "";
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x38;
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
        url = buffer.readString();
        hash = buffer.readString();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeString(url);
        buffer.writeString(hash);
        return buffer;
    }

    public String getUrl() {
        return url;
    }

    public String getHash() {
        return hash;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
