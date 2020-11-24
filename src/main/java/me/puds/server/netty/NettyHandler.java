package me.puds.server.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.puds.server.PuddingServer;
import me.puds.server.api.Server;
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

            PuddingServer.getLogger().info("Accepted connection from " + address);
        }

        Connection connection = Server.getConnections().get(address);
        Protocol protocol = connection.getProtocolVersion().getProtocol();
        ConnectionState state = connection.getState();

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
        Server.getConnections().remove(address);
    }
}
