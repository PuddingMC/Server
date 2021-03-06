package me.puds.server.server.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class NettyInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) {
        channel.pipeline().addLast(new PacketDecoder(), new NettyHandler());
    }
}
