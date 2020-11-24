package me.puds.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.puds.server.PuddingServer;

import java.net.BindException;

public class NettyServer {
    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    @SuppressWarnings("ConstantConditions")
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new NettyInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture future;
        try {
            future = bootstrap.bind(port).sync();
        } catch (Exception e) {
            if (e instanceof BindException) {
                PuddingServer.getLogger().fatal("Failed to bind to port " + port + ". Is it already in use?");
            } else {
                PuddingServer.getLogger().fatal("An unexpected error occurred while binding the port", e);
            }
            return;
        }

        PuddingServer.getLogger().info("Listening on " + "*:" + port);

        future.channel().closeFuture().addListener(f -> {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        });
    }
}
