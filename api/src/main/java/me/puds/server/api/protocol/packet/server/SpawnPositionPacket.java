package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;

public class SpawnPositionPacket extends Packet {
    private Vector location;

    public SpawnPositionPacket(Vector location) {
        this.location = location;
    }

    public SpawnPositionPacket() {
        location = new Vector();
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            return 0x42;
        } else {
            return 0x46;
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
        location = buffer.readPosition(version);
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writePosition(location, version);
        return buffer;
    }

    public Vector getLocation() {
        return location;
    }

    public void setLocation(Vector location) {
        this.location = location;
    }
}
