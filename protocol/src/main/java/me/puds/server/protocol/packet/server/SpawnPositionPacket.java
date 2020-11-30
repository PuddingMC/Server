package me.puds.server.protocol.packet.server;

import me.puds.server.protocol.*;

public class SpawnPositionPacket extends Packet {
    private Vector3 location;

    public SpawnPositionPacket(Vector3 location) {
        this.location = location;
    }

    public SpawnPositionPacket() {
        location = new Vector3();
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            return 0x42;
        } else if (version.isNewerThan(ProtocolVersion.RELEASE_1_12_2)) {
            return 0x46;
        } else {
            return 0x05;
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

    public Vector3 getLocation() {
        return location;
    }

    public void setLocation(Vector3 location) {
        this.location = location;
    }
}
