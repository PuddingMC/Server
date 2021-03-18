package me.puds.server.server.listener.packet;

import me.puds.server.api.Player;
import me.puds.server.api.Server;
import me.puds.server.api.event.EventHandler;
import me.puds.server.api.event.EventListener;
import me.puds.server.api.event.EventPriority;
import me.puds.server.api.text.TextComponent;
import me.puds.server.api.protocol.Connection;
import me.puds.server.api.protocol.packet.client.ClientChatMessagePacket;

public class ClientChatMessageListener implements EventListener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCanceled = true)
    public void onClientChatMessageListener(ClientChatMessagePacket packet, Connection connection) {
        if (!(connection instanceof Player)) {
            return;
        }

        Player sender = (Player) connection;

        if (packet.getMessage().startsWith("/")) {
            Server.getLogger().info(sender.getName() + ": " + packet.getMessage());

            String command = packet.getMessage().substring(1);
            Server.getCommandManager().handleCommand(sender, command);
            return;
        }

        // TODO: Add a limit for the message length
        String messageString = "<" + sender.getName() + "> " + packet.getMessage();
        TextComponent message = new TextComponent(messageString);

        Server.broadcastMessage(message);
    }
}
