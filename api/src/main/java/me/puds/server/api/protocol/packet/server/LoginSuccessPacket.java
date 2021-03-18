package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;

import java.util.UUID;

public class LoginSuccessPacket extends Packet {
    private UUID uniqueId;
    private String name;

    public LoginSuccessPacket(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

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

    public UUID getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
