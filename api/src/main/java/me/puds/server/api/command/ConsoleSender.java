package me.puds.server.api.command;

import me.puds.server.api.Server;
import me.puds.server.api.text.TextComponent;

import java.util.UUID;

public class ConsoleSender implements CommandSender {
    @Override
    public void sendMessage(TextComponent message) {
        sendMessage(message.toAnsiString());
    }

    @Override
    public void sendMessage(TextComponent message, UUID sender) {
        sendMessage(message);
    }

    @Override
    public void sendMessage(String message) {
        Server.getLogger().info(message);
    }

    @Override
    public void sendMessage(String message, UUID sender) {
        sendMessage(message);
    }
}
