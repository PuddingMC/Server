package me.puds.server.server.listener.packet;

import me.puds.server.api.Player;
import me.puds.server.api.Server;
import me.puds.server.api.event.EventHandler;
import me.puds.server.api.event.EventListener;
import me.puds.server.api.event.EventPriority;
import me.puds.server.protocol.Connection;
import me.puds.server.protocol.ConnectionState;
import me.puds.server.protocol.ProtocolVersion;
import me.puds.server.protocol.packet.client.LoginStartPacket;
import me.puds.server.protocol.packet.server.LoginSuccessPacket;
import me.puds.server.common.text.TextColor;
import me.puds.server.common.text.TextComponent;

import java.util.UUID;

public class LoginStartListener implements EventListener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCanceled = true)
    public void onLoginStart(LoginStartPacket packet, Connection connection) {
        if (connection.getProtocolVersion() == ProtocolVersion.UNSUPPORTED) {
            // TODO: Make this customizable
            TextComponent message = new TextComponent("Your client version is not supported!", TextColor.RED);
            connection.disconnect(message);
        }

        UUID uniqueId = UUID.randomUUID(); // TODO: Assign a proper UUID
        Player player = new Player(connection, uniqueId, packet.getName());

        LoginSuccessPacket successPacket = new LoginSuccessPacket(player.getUniqueId(), player.getName());
        player.sendPacket(successPacket);
        player.setState(ConnectionState.PLAY);

        int entityId = Server.nextEntityId();
        // TODO: Send game join packet

        Server.getConnections().put(connection.getAddress(), player);
    }
}
