package me.puds.server.api.protocol.packet.client;

import me.puds.server.api.protocol.*;

public class HandshakePacket extends Packet {
    private int protocolVersion;
    private String serverAddress;
    private int serverPort;
    private HandshakeIntent nextState;

    public HandshakePacket(int protocolVersion, String serverAddress, int serverPort, HandshakeIntent nextState) {
        this.protocolVersion = protocolVersion;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.nextState = nextState;
    }

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

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public HandshakeIntent getNextState() {
        return nextState;
    }

    public void setNextState(HandshakeIntent nextState) {
        this.nextState = nextState;
    }
}
