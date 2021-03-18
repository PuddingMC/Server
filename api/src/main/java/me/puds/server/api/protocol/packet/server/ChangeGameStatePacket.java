package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;

public class ChangeGameStatePacket extends Packet {
    private short reason;
    private float value;

    public ChangeGameStatePacket(short reason, float value) {
        this.reason = reason;
        this.value = value;
    }

    public ChangeGameStatePacket() {
        this.reason = 0;
        this.value = 0;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x1d;
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
        reason = buffer.readUnsignedByte();
        value = buffer.readFloat();
        }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeByte(reason);
        buffer.writeFloat(value);
        return buffer;
    }

    public short getReason() {
        return reason;
    }

    public float getValue() {
        return value;
    }

    public void setReason(short reason) {
        this.reason = reason;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
