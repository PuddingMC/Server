package me.puds.server.api;

import me.puds.server.api.protocol.Connection;

import java.util.UUID;

public class Player extends Connection {
    private final UUID uniqueId;
    private final String name;

    public Player(Connection connection, UUID uniqueId, String name) {
        super(connection.getAddress(), connection.getProtocolVersion(), connection.getChannel());

        this.uniqueId = uniqueId;
        this.name = name;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }
}
