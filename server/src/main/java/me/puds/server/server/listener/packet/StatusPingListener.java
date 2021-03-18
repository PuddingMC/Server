package me.puds.server.server.listener.packet;

import me.puds.server.api.event.EventHandler;
import me.puds.server.api.event.EventListener;
import me.puds.server.api.event.EventPriority;
import me.puds.server.api.protocol.Connection;
import me.puds.server.api.protocol.packet.client.StatusPingPacket;
import me.puds.server.api.protocol.packet.server.StatusPongPacket;

public class StatusPingListener implements EventListener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCanceled = true)
    public void onStatusPing(StatusPingPacket packet, Connection connection) {
        StatusPongPacket pong = new StatusPongPacket(packet.getPayload());
        connection.sendPacket(pong);
    }
}
