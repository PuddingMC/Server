package me.puds.server.api.protocol.client;

import me.puds.server.api.protocol.*;

public class LoginStartPacket extends Packet {
    private String name;

    public LoginStartPacket() {
        name = "";
    }

    public LoginStartPacket(String name) {
        this.name = name;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x00;
    }

    @Override
    public ConnectionState getState() {
        return ConnectionState.LOGIN;
    }

    @Override
    public PacketSender getSender() {
        return PacketSender.CLIENT;
    }

    @Override
    public void fromBuffer(ProtocolVersion version, PacketBuffer buffer) {
        name = buffer.readString();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeString(name);
        return buffer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
