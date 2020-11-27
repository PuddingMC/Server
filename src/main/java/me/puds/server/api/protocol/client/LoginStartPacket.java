package me.puds.server.api.protocol.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.puds.server.api.protocol.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class LoginStartPacket extends Packet {
    private String name;

    public LoginStartPacket() {
        name = "";
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
}
