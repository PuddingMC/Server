package me.puds.server;

import me.puds.server.api.Server;
import me.puds.server.listener.packet.HandshakeListener;
import me.puds.server.netty.NettyServer;

public class Main {
    // TODO: Clean up the main method (it's currently written for testing)
    public static void main(String[] args) throws InterruptedException {
        Server.getEventManager().registerListener(new HandshakeListener());
        new NettyServer(25565).start();
    }
}
