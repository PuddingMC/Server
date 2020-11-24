package me.puds.server;

import me.puds.server.api.Logger;
import me.puds.server.api.Server;
import me.puds.server.listener.packet.*;
import me.puds.server.netty.NettyServer;

public class PuddingServer {
    private static final String name = "PuddingServer";
    private static final String version = "1.0"; // TODO: Get this dynamically from Gradle
    private static Logger logger;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        logger = new Logger("Server");
        logger.info("Starting " + name + " v" + version + "");

        Server.getEventManager().registerListener(new HandshakeListener());
        Server.getEventManager().registerListener(new LoginStartListener());
        Server.getEventManager().registerListener(new StatusPingListener());
        Server.getEventManager().registerListener(new StatusRequestListener());

        NettyServer server = new NettyServer(25565); // TODO: Read the port from some config file
        server.start();

        long elapsedTime = System.currentTimeMillis() - startTime;
        logger.info("Done! (" + elapsedTime / 1000.0 + "s)");
    }

    public static String getName() {
        return name;
    }

    public static String getVersion() {
        return version;
    }

    public static Logger getLogger() {
        return logger;
    }
}
