package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;

public class SetCompressionPacket extends Packet {
    private int threshold;

    public SetCompressionPacket(int threshold) {
        this.threshold = threshold;
    }

    public SetCompressionPacket() {
        this.threshold = 0;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x03;
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
        threshold = buffer.readVarInt();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(threshold);
        return buffer;
    }

    public int getThreshold() {
        return threshold;
    }
    
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
