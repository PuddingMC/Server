package me.puds.server.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import me.puds.server.protocol.packet.server.LoginDisconnectPacket;
import me.puds.server.common.text.TextComponent;

import java.net.InetSocketAddress;

public class Connection {
    private final InetSocketAddress address;
    private final ProtocolVersion protocolVersion;
    private final Channel channel;

    private String serverAddress = "";
    private int serverPort = 0;
    private ConnectionState state = ConnectionState.HANDSHAKING;

    public Connection(InetSocketAddress address, ProtocolVersion protocolVersion, Channel channel) {
        this.address = address;
        this.protocolVersion = protocolVersion;
        this.channel = channel;
    }

    public void sendPacket(Packet packet) {
        // Hacky workaround to get the final buffer size
        // TODO: Don't do this lol
        PacketBuffer tempBuffer = packet.toBuffer(protocolVersion);
        byte[] data = tempBuffer.toByteArray();
        int packetId = packet.getPacketId(protocolVersion);
        tempBuffer.writeVarInt(packetId);

        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(tempBuffer.getSize());
        buffer.writeVarInt(packetId);
        buffer.writeBytes(data);

        // TODO: Compress and encrypt the packet if applicable
        ByteBuf byteBuf = Unpooled.copiedBuffer(buffer.toByteArray());
        channel.writeAndFlush(byteBuf);
    }

    public void disconnect(TextComponent reason) {
        if (state == ConnectionState.LOGIN) {
            LoginDisconnectPacket packet = new LoginDisconnectPacket(reason);
            sendPacket(packet);
        } else if (state == ConnectionState.PLAY) {
            // TODO: Send disconnect packet
        }
        channel.disconnect();
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public ProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    public Channel getChannel() {
        return channel;
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
