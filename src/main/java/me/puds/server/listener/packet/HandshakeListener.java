package me.puds.server.listener.packet;

import me.puds.server.api.event.EventHandler;
import me.puds.server.api.event.EventListener;
import me.puds.server.api.event.EventPriority;
import me.puds.server.api.protocol.Connection;
import me.puds.server.api.protocol.client.HandshakePacket;

public class HandshakeListener implements EventListener {
    @EventHandler(priority = EventPriority.SERVER)
    public void onHandshakePacket(HandshakePacket packet, Connection connection) {
        // TODO: Properly handle handshaking and remove debug printing
        System.out.println("Received handshake packet.");
        System.out.println("  Protocol version: " + packet.getProtocolVersion());
        System.out.println("  Server address: " + packet.getServerAddress());
        System.out.println("  Server port: " + packet.getServerPort());
        System.out.println("  Next state: " + packet.getNextState());
        System.out.println();
    }
}
