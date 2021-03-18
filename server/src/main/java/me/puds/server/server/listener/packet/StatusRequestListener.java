package me.puds.server.server.listener.packet;

import me.puds.server.api.event.EventHandler;
import me.puds.server.api.event.EventListener;
import me.puds.server.api.event.EventPriority;
import me.puds.server.api.protocol.Connection;
import me.puds.server.api.protocol.PingResponse;
import me.puds.server.api.protocol.packet.client.StatusRequestPacket;
import me.puds.server.api.protocol.packet.server.StatusResponsePacket;

import java.util.Map;

public class StatusRequestListener implements EventListener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCanceled = true)
    public void onStatusRequest(StatusRequestPacket packet, Connection connection) {
        // TODO: Call an event for this and remove debug code
        PingResponse response = new PingResponse();
        String versionName = connection.getProtocolVersion().getDisplayName();
        response.setDescription("§3A test StatusRequestPacket response.§r\n" +
                "You are playing on Minecraft §a" + versionName + "§r.");
        response.setProtocol(connection.getProtocolVersion().getVersion());
        response.setVersionName(versionName);
        response.setPlayerSample(Map.of());
        response.setOnlinePlayers(8615);
        response.setMaxPlayers(10000);

        StatusResponsePacket responsePacket = new StatusResponsePacket(response);
        connection.sendPacket(responsePacket);
    }
}
