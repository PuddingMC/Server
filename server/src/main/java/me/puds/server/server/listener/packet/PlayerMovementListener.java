package me.puds.server.server.listener.packet;

import me.puds.server.api.Player;
import me.puds.server.api.event.EventHandler;
import me.puds.server.api.event.EventListener;
import me.puds.server.api.event.EventPriority;
import me.puds.server.api.protocol.Connection;
import me.puds.server.api.protocol.packet.client.PlayerMovementPacket;

public class PlayerMovementListener implements EventListener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCanceled = true)
    public void onPlayerMovement(PlayerMovementPacket packet, Connection connection) {
        if (connection instanceof Player) {
            Player player = (Player) connection;
        }
    }
}
