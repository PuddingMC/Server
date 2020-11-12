package me.puds.server.api;

import me.puds.server.api.event.EventManager;
import me.puds.server.api.protocol.Connection;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final EventManager eventManager = new EventManager();
    private static final Map<InetSocketAddress, Connection> connections = new HashMap<>();

    public static EventManager getEventManager() {
        return eventManager;
    }

    public static Map<InetSocketAddress, Connection> getConnections() {
        return connections;
    }
}
