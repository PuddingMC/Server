package me.puds.server.api.protocol.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.puds.server.api.protocol.ChatPosition;
import me.puds.server.api.protocol.*;
import me.puds.server.api.text.TextComponent;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class ServerChatMessagePacket extends Packet {
    private TextComponent message;
    private ChatPosition position;
    private UUID chatSender;

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
}
