package me.puds.server.api;

import me.puds.server.api.event.EventManager;
import me.puds.server.api.protocol.Connection;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Server {
    private static final EventManager eventManager = new EventManager();
    private static final Map<InetSocketAddress, Connection> connections = new HashMap<>();

    private static int nextEntityId = 0;

    public static EventManager getEventManager() {
        return eventManager;
    }

    public static Map<InetSocketAddress, Connection> getConnections() {
        return connections;
    }

    public static List<Player> getOnlinePlayers() {
        return connections.values().stream()
                .filter(c -> c instanceof Player)
                .map(c -> (Player) c)
                .collect(Collectors.toList());
    }

    public static int nextEntityId() {
        return nextEntityId++;
    }
}
