package me.puds.server.api.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.Data;
import me.puds.server.api.protocol.server.LoginDisconnectPacket;
import me.puds.server.api.text.TextComponent;

import java.net.InetSocketAddress;

@Data
public class Connection {
    private final InetSocketAddress address;
    private final ProtocolVersion protocolVersion;
    private final Channel channel;

    private String serverAddress = "";
    private int serverPort = 0;
    private ConnectionState state = ConnectionState.HANDSHAKING;

    public void sendPacket(Packet packet) {
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
}
