package me.puds.server.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.puds.server.api.protocol.PacketBuffer;

public class NettyHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        PacketBuffer buffer = new PacketBuffer(msg);
        if (buffer.readVarInt() != buffer.getSize()) {
            return;
        }

        int packetId = buffer.readVarInt();
        // TODO: Handle the packet
    }
}
