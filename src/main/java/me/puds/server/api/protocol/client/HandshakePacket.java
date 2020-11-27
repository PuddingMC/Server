package me.puds.server.api.protocol.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.puds.server.api.protocol.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class HandshakePacket extends Packet {
    private int protocolVersion;
    private String serverAddress;
    private int serverPort;
    private HandshakeIntent nextState;

    public HandshakePacket() {
        protocolVersion = 0;
        serverAddress = "";
        serverPort = 0;
        nextState = HandshakeIntent.STATUS;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x00;
    }

    @Override
    public ConnectionState getState() {
        return ConnectionState.HANDSHAKING;
    }

    @Override
    public PacketSender getSender() {
        return PacketSender.CLIENT;
    }

    @Override
    public void fromBuffer(ProtocolVersion version, PacketBuffer buffer) {
        protocolVersion = buffer.readVarInt();
        serverAddress = buffer.readString();
        serverPort = buffer.readUnsignedShort();
        nextState = HandshakeIntent.of(buffer.readVarInt());
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(protocolVersion);
        buffer.writeString(serverAddress);
        buffer.writeShort(serverPort);
        buffer.writeVarInt(nextState.getValue());
        return buffer;
    }
}
