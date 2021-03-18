package me.puds.server.api.command;

import me.puds.server.api.Player;
import me.puds.server.api.text.TextColor;
import me.puds.server.api.text.TextComponent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();

    public void registerCommand(Command command) {
        commands.put(command.getName(), command);
        if (command.getAliases() != null) {
            for (String alias : command.getAliases()) {
                commands.put(alias, command);
            }
        }
    }

    public void handleCommand(CommandSender sender, String fullCommand) {
        String[] split = fullCommand.split(" ");
        if (split.length <= 0) {
            return;
        }

        if (!commands.containsKey(split[0])) {
            TextComponent message = new TextComponent("Unknown command.", TextColor.RED); // TODO: Better message
            sender.sendMessage(message);
            return;
        }

        Command command = commands.get(split[0]);
        // TODO: Better messages
        if (!command.isPlayerExecutable() && sender instanceof Player) {
            TextComponent message = new TextComponent("This command must be run in the console.", TextColor.RED);
            sender.sendMessage(message);
            return;
        }
        if (!command.isConsoleExecutable() && sender instanceof ConsoleSender) {
            TextComponent message = new TextComponent("This command must be run by a player.", TextColor.RED);
            sender.sendMessage(message);
            return;
        }

        // TODO: Check for permission

        List<String> args = Arrays.asList(Arrays.copyOfRange(split, 1, split.length));
        command.onExecute(sender, split[0], args);
    }
}
