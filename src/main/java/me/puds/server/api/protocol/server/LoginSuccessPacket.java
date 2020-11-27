package me.puds.server.api.protocol.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.puds.server.api.protocol.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class LoginSuccessPacket extends Packet {
    private UUID uniqueId;
    private String name;

    public LoginSuccessPacket() {
        uniqueId = new UUID(0, 0);
        name = "";
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x02;
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
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            uniqueId = buffer.readUuid();
        } else {
            uniqueId = UUID.fromString(buffer.readString());
        }
        name = buffer.readString();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            buffer.writeUuid(uniqueId);
        } else {
            buffer.writeString(uniqueId.toString());
        }
        buffer.writeString(name);
        return buffer;
    }
}
