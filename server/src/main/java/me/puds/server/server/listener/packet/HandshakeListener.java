package me.puds.server.server.listener.packet;

import me.puds.server.api.event.EventHandler;
import me.puds.server.api.event.EventListener;
import me.puds.server.api.event.EventPriority;
import me.puds.server.api.protocol.Connection;
import me.puds.server.api.protocol.ConnectionState;
import me.puds.server.api.protocol.HandshakeIntent;
import me.puds.server.api.protocol.packet.client.HandshakePacket;

public class HandshakeListener implements EventListener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCanceled = true)
    public void onHandshakePacket(HandshakePacket packet, Connection connection) {
        connection.setServerAddress(packet.getServerAddress());
        connection.setServerPort(packet.getServerPort());
        if (packet.getNextState() == HandshakeIntent.STATUS) {
            connection.setState(ConnectionState.STATUS);
        } else {
            connection.setState(ConnectionState.LOGIN);
        }
    }
}
