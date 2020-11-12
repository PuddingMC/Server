package me.puds.server.api.protocol;

import java.net.InetSocketAddress;

public class Connection {
    private final InetSocketAddress address;
    private final ProtocolVersion protocolVersion;

    private String serverAddress;
    private int serverPort;
    private ConnectionState state;

    public Connection(InetSocketAddress address, ProtocolVersion protocolVersion) {
        this.address = address;
        this.protocolVersion = protocolVersion;
    }

    public void send(Packet packet) {
        // Hacky workaround to get the final buffer size
        PacketBuffer tempBuffer = packet.toBuffer(protocolVersion);
        byte[] data = tempBuffer.toByteArray();
        int packetId = packet.getPacketId(protocolVersion);
        tempBuffer.writeVarInt(packetId);

        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(tempBuffer.getSize());
        buffer.writeVarInt(packetId);
        buffer.writeBytes(data);

        // TODO: Compress and encrypt the packet if applicable
        // TODO: Actually send the packet
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public ProtocolVersion getProtocolVersion() {
        return protocolVersion;
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

    public ConnectionState getState() {
        return state;
    }

    public void setState(ConnectionState state) {
        this.state = state;
    }
}
