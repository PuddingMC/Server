package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;

public class EffectPacket extends Packet {
    private int effectId;
    private Vector location;
    private int data;
    private boolean disableRelativeVolume;

    public EffectPacket(int effectId, Vector location, int data, boolean disableRelativeVolume) {
        this.effectId = effectId;
        this.location = location;
        this.data = data;
        this.disableRelativeVolume = disableRelativeVolume;
    }

    public EffectPacket() {
        this.effectId = 0;
        this.location = new Vector(0, 0, 0);
        this.data = 0;
        this.disableRelativeVolume = false;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x21;
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
        effectId = buffer.readInt();
        location = buffer.readPosition();
        data = buffer.readInt();
        disableRelativeVolume = buffer.readBoolean();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeInt(effectId);
        buffer.writePosition(location);
        buffer.writeInt(data);
        buffer.writeBoolean(disableRelativeVolume);
        return buffer;
    }

    public int getEffectId() {
        return effectId;
    }

    public Vector getLocation() {
        return location;
    }

    public int getData() {
        return data;
    }

    public boolean isDisableRelativeVolume() {
        return disableRelativeVolume;
    }

    public void setEffectId(int effectId) {
        this.effectId = effectId;
    }

    public void setLocation(Vector location) {
        this.location = location;
    }

    public void setData(int data) {
        this.data = data;
    }

    public void setDisableRelativeVolume(boolean disableRelativeVolume) {
        this.disableRelativeVolume = disableRelativeVolume;
    }
}
