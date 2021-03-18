package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.text.TextComponent;
import me.puds.server.api.protocol.*;

public class DisconnectPacket extends Packet {
    private TextComponent chat;

    public DisconnectPacket(TextComponent chat) {
        this.chat = chat;
    }

    public DisconnectPacket() {
        this.chat = new TextComponent();
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            return 0x19;
        } else {
            return 0x1a;
        }
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
        chat = buffer.readChat();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeChat(chat);
        return (buffer);
    }

    public TextComponent getChat() {
        return chat;
    }

    public void setChat(TextComponent chat) {
        this.chat = chat;
    }
}
