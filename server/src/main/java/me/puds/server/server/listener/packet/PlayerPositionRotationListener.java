package me.puds.server.server.listener.packet;

import me.puds.server.api.Server;
import me.puds.server.api.event.EventHandler;
import me.puds.server.api.event.EventListener;
import me.puds.server.api.event.EventPriority;
import me.puds.server.api.protocol.Connection;
import me.puds.server.api.protocol.packet.client.PlayerPositionPacket;
import me.puds.server.api.protocol.packet.client.PlayerPositionRotationPacket;
import me.puds.server.api.protocol.packet.client.PlayerRotationPacket;

public class PlayerPositionRotationListener implements EventListener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCanceled = true)
    public void onPlayerPositionRotation(PlayerPositionRotationPacket packet, Connection connection) {
        PlayerPositionPacket playerPositionPacket = new PlayerPositionPacket(packet.getX(), packet.getFootY(),
                packet.getZ(), packet.isOnGround());
        Server.getEventManager().callEvent(PlayerPositionPacket.class, playerPositionPacket, connection);

        PlayerRotationPacket playerRotationPacket = new PlayerRotationPacket(packet.getYaw(), packet.getPitch(),
                packet.isOnGround());
        Server.getEventManager().callEvent(PlayerRotationPacket.class, playerRotationPacket, connection);
    }
}
