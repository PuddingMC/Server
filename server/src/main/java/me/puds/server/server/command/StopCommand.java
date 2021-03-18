package me.puds.server.server.command;

import me.puds.server.api.Server;
import me.puds.server.api.command.Command;
import me.puds.server.api.command.CommandSender;
import me.puds.server.api.text.TextColor;
import me.puds.server.api.text.TextComponent;

import java.util.List;

public class StopCommand extends Command {
    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Stops the server.";
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getPermission() {
        return null; // TODO: Add a permission to the stop command
    }

    @Override
    public boolean isPlayerExecutable() {
        return true;
    }

    @Override
    public boolean isConsoleExecutable() {
        return true;
    }

    @Override
    public boolean onExecute(CommandSender sender, String alias, List<String> args) {
        TextComponent stopMessage = new TextComponent("Stopping the server...", TextColor.RED);
        sender.sendMessage(stopMessage);

        Server.stop();
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String alias, List<String> args) {
        return List.of();
    }
}
