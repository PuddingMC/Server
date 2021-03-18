package me.puds.server.api;

import me.puds.server.api.command.CommandManager;
import me.puds.server.api.command.ConsoleSender;
import me.puds.server.api.event.EventManager;
import me.puds.server.api.protocol.Connection;
import me.puds.server.api.text.TextBuilder;
import me.puds.server.api.text.TextColor;
import me.puds.server.api.text.TextComponent;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Server {
    private static final CommandManager commandManager = new CommandManager();
    private static final EventManager eventManager = new EventManager();

    private static final Logger logger = new Logger("Server");
    private static final Map<InetSocketAddress, Connection> connections = new HashMap<>();
    private static final ConsoleSender consoleSender = new ConsoleSender();

    private static int nextEntityId = 0;

    private Server() {}

    // "Custom" methods (aka anything besides simple getters or setters)

    public static List<Player> getOnlinePlayers() {
        return connections.values().stream()
                .filter(c -> c instanceof Player)
                .map(c -> (Player) c)
                .collect(Collectors.toList());
    }

    public static int nextEntityId() {
        return nextEntityId++;
    }
    
    public static void broadcastMessage(TextComponent message) {
        for (Player player : getOnlinePlayers()) {
            player.sendMessage(message);
        }
        getLogger().info(message.toAnsiString());
    }

    public static void broadcastMessage(String message) {
        for (Player player : getOnlinePlayers()) {
            player.sendMessage(message);
        }
        getLogger().info(message);
    }

    public static void stop(TextComponent reason) {
        TextComponent message = TextBuilder.builder()
                .add("Stopping server for reason \"")
                .add(reason)
                .reset()
                .add("\"")
                .build();
        getLogger().info(message.toAnsiString());

        for (Player player : getOnlinePlayers()) {
            player.disconnect(reason);
        }

        getLogger().info("Goodbye!");
        System.exit(0);
    }

    public static void stop() {
        TextComponent reason = new TextComponent("Server is stopping", TextColor.RED);
        stop(reason);
    }

    // Getters/setters

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static EventManager getEventManager() {
        return eventManager;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static Map<InetSocketAddress, Connection> getConnections() {
        return connections;
    }

    public static ConsoleSender getConsoleSender() {
        return consoleSender;
    }
}
