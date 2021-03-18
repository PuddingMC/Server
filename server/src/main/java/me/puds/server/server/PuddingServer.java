package me.puds.server.server;

import me.puds.server.api.Server;
import me.puds.server.api.world.Schematic;
import me.puds.server.server.command.StopCommand;
import me.puds.server.server.listener.packet.*;
import me.puds.server.server.netty.NettyServer;
import me.puds.server.server.tick.ComponentManager;
import me.puds.server.server.tick.TickLoop;
import me.puds.server.server.tick.component.KeepAliveComponent;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PuddingServer {
    public static final String NAME = "PuddingServer";
    public static final String VERSION = "1.0"; // TODO: Get this dynamically from Gradle

    private static final ComponentManager componentManager = new ComponentManager();

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Server.getLogger().info("Starting " + NAME + " v" + VERSION + "");

        Server.getEventManager().registerListener(new ClientChatMessageListener());
        Server.getEventManager().registerListener(new ClientKeepAliveListener());
        Server.getEventManager().registerListener(new HandshakeListener());
        Server.getEventManager().registerListener(new LoginStartListener());
        Server.getEventManager().registerListener(new PlayerMovementListener());
        Server.getEventManager().registerListener(new PlayerRotationListener());
        Server.getEventManager().registerListener(new StatusPingListener());
        Server.getEventManager().registerListener(new StatusRequestListener());
        Server.getEventManager().registerListener(new PlayerRotationListener());
        Server.getEventManager().registerListener(new PlayerPositionRotationListener());

        componentManager.add(new KeepAliveComponent());

        Server.getCommandManager().registerCommand(new StopCommand());

        NettyServer server = new NettyServer(25565); // TODO: Read the port from some config file
        server.start();

        long elapsedTime = System.currentTimeMillis() - startTime;
        Server.getLogger().info("Done! (" + elapsedTime / 1000.0 + "s)");

        Thread commandThread = new Thread(() -> {
            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
            Scanner scanner = new Scanner(System.in);

            executor.scheduleAtFixedRate(() -> {
                String input = scanner.nextLine();
                Server.getCommandManager().handleCommand(Server.getConsoleSender(), input);
            }, 0, 1, TimeUnit.MILLISECONDS);
        });
        commandThread.start();

        TickLoop loop = new TickLoop();
        loop.start();
    }

    public static ComponentManager getComponentManager() {
        return componentManager;
    }
}
