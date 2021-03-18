package me.puds.server.api.command;

import java.util.List;

public abstract class Command {
    public abstract String getName();
    public abstract List<String> getAliases();

    public abstract String getDescription();
    public abstract String getUsage();
    public abstract String getPermission();

    public abstract boolean isPlayerExecutable();
    public abstract boolean isConsoleExecutable();

    public abstract boolean onExecute(CommandSender sender, String alias, List<String> args);
    public abstract List<String> onTabComplete(CommandSender sender, String alias, List<String> args);
}
