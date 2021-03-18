package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;
import me.puds.server.api.text.TextComponent;

public class PlayerListHeaderFooterPacket extends Packet {
    private TextComponent header;
    private TextComponent footer;

    public PlayerListHeaderFooterPacket(TextComponent header, TextComponent footer) {
        this.header = header;
        this.footer = footer;
    }

    public PlayerListHeaderFooterPacket() {
        this.header = new TextComponent();
        this.header = new TextComponent();
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            return 0x53;
        } else {
            return 0x4a;
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
        header = buffer.readChat();
        footer = buffer.readChat();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeChat(header);
        buffer.writeChat(footer);
        return buffer;
    }

    public TextComponent getHeader() {
        return header;
    }

    public TextComponent getFooter() {
        return footer;
    }

    public void setHeader(TextComponent header) {
        this.header = header;
    }

    public void setFooter(TextComponent footer) {
        this.footer = footer;
    }
}
