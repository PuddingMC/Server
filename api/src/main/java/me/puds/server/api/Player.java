package me.puds.server.api;

import me.puds.server.api.command.CommandSender;
import me.puds.server.api.protocol.ChatPosition;
import me.puds.server.api.protocol.Connection;
import me.puds.server.api.protocol.packet.server.ServerChatMessagePacket;
import me.puds.server.api.text.TextBuilder;
import me.puds.server.api.text.TextComponent;
import me.puds.server.api.world.Location;

import java.util.UUID;

public class Player extends Connection implements CommandSender {
    private final UUID uniqueId;
    private final String name;

    private long lastKeepAlive;

    private Location location;
    private boolean onGround;

    public Player(Connection connection, UUID uniqueId, String name) {
        super(connection.getAddress(), connection.getProtocolVersion(), connection.getChannel());

        this.uniqueId = uniqueId;
        this.name = name;

        lastKeepAlive = 0;

        location = new Location(null, 0, 0, 0, 0, 0);
        onGround = true;
    }

    @Override
    public void disconnect(TextComponent reason) {
        TextComponent message = TextBuilder.builder()
                .add(getName())
                .add(" has been disconnected for reason \"")
                .add(reason)
                .reset()
                .add("\"")
                .build();
        Server.getLogger().info(message.toAnsiString());

        super.disconnect(reason);
    }

    @Override
    public void sendMessage(TextComponent message) {
        sendMessage(message, new UUID(0, 0));
    }

    @Override
    public void sendMessage(TextComponent message, UUID sender) {
        ServerChatMessagePacket packet = new ServerChatMessagePacket(message, ChatPosition.CHAT, sender);
        sendPacket(packet);
    }

    @Override
    public void sendMessage(String message) {
        sendMessage(message, new UUID(0, 0));
    }

    @Override
    public void sendMessage(String message, UUID sender) {
        // TODO: Format this using legacy color codes
        TextComponent component = new TextComponent(message);
        sendMessage(component, sender);
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public long getLastKeepAlive() {
        return lastKeepAlive;
    }

    public void setLastKeepAlive(long lastKeepAlive) {
        this.lastKeepAlive = lastKeepAlive;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
