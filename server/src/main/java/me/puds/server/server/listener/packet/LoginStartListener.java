package me.puds.server.server.listener.packet;

import me.puds.server.api.Player;
import me.puds.server.api.Server;
import me.puds.server.api.event.EventHandler;
import me.puds.server.api.event.EventListener;
import me.puds.server.api.event.EventPriority;
import me.puds.server.api.protocol.packet.server.ResourcePackSendPacket;
import me.puds.server.api.text.TextColor;
import me.puds.server.api.text.TextComponent;
import me.puds.server.api.protocol.*;
import me.puds.server.api.protocol.packet.client.LoginStartPacket;
import me.puds.server.api.protocol.packet.server.JoinGamePacket;
import me.puds.server.api.protocol.packet.server.LoginSuccessPacket;
import me.puds.server.api.protocol.packet.server.PlayerPositionLookPacket;

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
        player.setLastKeepAlive(System.currentTimeMillis());

        int entityId = Server.nextEntityId();
        // TODO: Send game join packet

        TextComponent joinMessage = new TextComponent(player.getName() + " has joined."); // TODO: Customizable message
        Server.broadcastMessage(joinMessage);

        Server.getConnections().put(connection.getAddress(), player);
    }
}
