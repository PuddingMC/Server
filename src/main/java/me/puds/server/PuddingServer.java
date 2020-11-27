package me.puds.server;

import me.puds.server.api.Server;
import me.puds.server.listener.packet.HandshakeListener;
import me.puds.server.listener.packet.LoginStartListener;
import me.puds.server.listener.packet.StatusPingListener;
import me.puds.server.listener.packet.StatusRequestListener;
import me.puds.server.netty.NettyServer;
import me.puds.server.tick.TickLoop;

public class PuddingServer {
    private static final String name = "PuddingServer";
    private static final String version = "1.0"; // TODO: Get this dynamically from Gradle

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Server.getLogger().info("Starting " + name + " v" + version + "");

        Server.getEventManager().registerListener(new HandshakeListener());
        Server.getEventManager().registerListener(new LoginStartListener());
        Server.getEventManager().registerListener(new StatusPingListener());
        Server.getEventManager().registerListener(new StatusRequestListener());

        NettyServer server = new NettyServer(25565); // TODO: Read the port from some config file
        server.start();

        long elapsedTime = System.currentTimeMillis() - startTime;
        Server.getLogger().info("Done! (" + elapsedTime / 1000.0 + "s)");

        TickLoop loop = new TickLoop();
        loop.start();
    }

    public static String getName() {
        return name;
    }

    public static String getVersion() {
        return version;
    }
}
