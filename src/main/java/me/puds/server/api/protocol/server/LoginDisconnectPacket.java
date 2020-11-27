package me.puds.server.api.protocol.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.puds.server.api.protocol.*;
import me.puds.server.api.text.TextComponent;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class LoginDisconnectPacket extends Packet {
    private TextComponent reason;

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
}
