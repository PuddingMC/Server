package me.puds.server.api.command;

import me.puds.server.api.text.TextComponent;

import java.util.UUID;

public interface CommandSender {
    void sendMessage(TextComponent message);
    void sendMessage(TextComponent message, UUID sender);
    void sendMessage(String message);
    void sendMessage(String message, UUID sender);
}
