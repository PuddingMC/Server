package me.puds.server.protocol.packet.server;

import me.puds.server.protocol.*;
import me.puds.server.common.text.TextComponent;

public class LoginDisconnectPacket extends Packet {
    private TextComponent reason;

    public LoginDisconnectPacket(TextComponent reason) {
        this.reason = reason;
    }

    public LoginDisconnectPacket() {
        reason = new TextComponent();
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
        return PacketSender.SERVER;
    }

    @Override
    public void fromBuffer(ProtocolVersion version, PacketBuffer buffer) {
        this.reason = buffer.readChat();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeChat(reason);
        return buffer;
    }

    public TextComponent getReason() {
        return reason;
    }

    public void setReason(TextComponent reason) {
        this.reason = reason;
    }
}
