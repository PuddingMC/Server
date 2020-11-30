package me.puds.server.server;

import me.puds.server.api.Server;
import me.puds.server.server.listener.packet.HandshakeListener;
import me.puds.server.server.listener.packet.LoginStartListener;
import me.puds.server.server.listener.packet.StatusPingListener;
import me.puds.server.server.listener.packet.StatusRequestListener;
import me.puds.server.server.netty.NettyServer;
import me.puds.server.server.tick.ComponentManager;
import me.puds.server.server.tick.TickLoop;

public class PuddingServer {
    public static final String NAME = "PuddingServer";
    public static final String VERSION = "1.0"; // TODO: Get this dynamically from Gradle

    private static final ComponentManager componentManager = new ComponentManager();

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Server.getLogger().info("Starting " + NAME + " v" + VERSION + "");

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

    public static ComponentManager getComponentManager() {
        return componentManager;
    }
}
