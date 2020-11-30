package me.puds.server.protocol.packet.server;

import me.puds.server.protocol.*;
import me.puds.server.common.text.TextComponent;

import java.util.UUID;

public class ServerChatMessagePacket extends Packet {
    private TextComponent message;
    private ChatPosition position;
    private UUID chatSender;

    public ServerChatMessagePacket(TextComponent message, ChatPosition position, UUID chatSender) {
        this.message = message;
        this.position = position;
        this.chatSender = chatSender;
    }

    public ServerChatMessagePacket() {
        message = new TextComponent();
        position = ChatPosition.CHAT;
        chatSender = new UUID(0, 0);
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            return 0x0e;
        } else if (version.isNewerThan(ProtocolVersion.RELEASE_1_12_2)) {
            return 0x0f;
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
        return PacketSender.SERVER;
    }

    @Override
    public void fromBuffer(ProtocolVersion version, PacketBuffer buffer) {
        message = buffer.readChat();
        position = ChatPosition.of(buffer.readByte());
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            chatSender = buffer.readUuid();
        }
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeChat(message);
        buffer.writeByte(position.getValue());
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            buffer.writeUuid(chatSender);
        }
        return buffer;
    }

    public TextComponent getMessage() {
        return message;
    }

    public void setMessage(TextComponent message) {
        this.message = message;
    }

    public ChatPosition getPosition() {
        return position;
    }

    public void setPosition(ChatPosition position) {
        this.position = position;
    }

    public UUID getChatSender() {
        return chatSender;
    }

    public void setChatSender(UUID chatSender) {
        this.chatSender = chatSender;
    }
}
