package me.puds.server.api;

import lombok.Getter;
import me.puds.server.api.event.EventManager;
import me.puds.server.api.protocol.Connection;
import me.puds.server.tick.ComponentManager;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Server {
    @Getter private static final EventManager eventManager = new EventManager();
    @Getter private static final ComponentManager componentManager = new ComponentManager();

    @Getter private static final Logger logger = new Logger("Server");
    @Getter private static final Map<InetSocketAddress, Connection> connections = new HashMap<>();

    private static int nextEntityId = 0;

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
