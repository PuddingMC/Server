package me.puds.server.api.protocol.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.puds.server.api.protocol.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class PlayerPositionLookPacket extends Packet {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private byte flags;
    private int teleportId;

    public PlayerPositionLookPacket() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.yaw = 0;
        this.pitch = 0;
        this.flags = 0;
        this.teleportId = 0;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            return 0x34;
        } else if (version.isNewerThan(ProtocolVersion.RELEASE_1_12_2)) {
            return 0x2f;
        } else {
            return 0x08;
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
        x = buffer.readDouble();
        y = buffer.readDouble();
        z = buffer.readDouble();
        yaw = buffer.readFloat();
        pitch = buffer.readFloat();
        flags = buffer.readByte();
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_12_2)) {
            teleportId = buffer.readVarInt();
        }
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
        buffer.writeFloat(yaw);
        buffer.writeFloat(pitch);
        buffer.writeByte(flags);
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_12_2)) {
            buffer.writeVarInt(teleportId);
        }
        return buffer;
    }
}
