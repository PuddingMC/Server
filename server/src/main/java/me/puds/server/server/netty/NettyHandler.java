package me.puds.server.server.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.puds.server.api.Player;
import me.puds.server.api.Server;
import me.puds.server.api.text.TextComponent;
import me.puds.server.api.protocol.*;

import java.net.InetSocketAddress;

public class NettyHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();

        PacketBuffer buffer = new PacketBuffer(msg);
        int packetId = buffer.readVarInt();

        if (!Server.getConnections().containsKey(address)) {
            if (packetId != 0) {
                ctx.disconnect();
                return;
            }

            PacketBuffer cloneBuffer = new PacketBuffer(buffer);
            int versionValue = cloneBuffer.readVarInt();

            ProtocolVersion protocolVersion = ProtocolVersion.of(versionValue);
            Connection connection = new Connection(address, protocolVersion, ctx.channel());
            Server.getConnections().put(address, connection);

            Server.getLogger().info("Accepted connection from " + address);
        }

        Connection connection = Server.getConnections().get(address);
        Protocol protocol = connection.getProtocolVersion().getProtocol();
        ConnectionState state = connection.getState();

        // TODO: Create a separate instance of the packet
        Packet packet = protocol.getPacket(state, PacketSender.CLIENT, packetId);
        if (packet == null) {
            // TODO: Handle this better
            return;
        }

        packet.fromBuffer(connection.getProtocolVersion(), buffer);
        Server.getEventManager().callEvent(packet.getClass(), packet, connection);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        Connection connection = Server.getConnections().get(address);
        if (connection instanceof Player) {
            Player player = (Player) connection;

            TextComponent leaveMessage = new TextComponent(player.getName() + " has left."); // TODO: Customizable message
            Server.broadcastMessage(leaveMessage);
        }

        Server.getConnections().remove(address);
    }
}
