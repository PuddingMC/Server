package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;

public class TimeUpdatePacket extends Packet {
    private long worldAge;
    private long timeOfDay;

    public TimeUpdatePacket(long worldAge, long timeOfDay) {
        this.worldAge = worldAge;
        this.timeOfDay = timeOfDay;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            return 0x4e;
        } else {
            return 0x47;
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
        this.worldAge = buffer.readLong();
        this.timeOfDay = buffer.readLong();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeLong(worldAge);
        buffer.writeLong(timeOfDay);
        return buffer;
    }

    public long getTimeOfDay() {
        return timeOfDay;
    }

    public long getWorldAge() {
        return worldAge;
    }

    public void setTimeOfDay(long timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void setWorldAge(long worldAge) {
        this.worldAge = worldAge;
    }
}
