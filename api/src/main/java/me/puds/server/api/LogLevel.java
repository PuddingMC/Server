package me.puds.server.api;

import me.puds.server.common.text.TextColor;

public enum LogLevel {
    DEBUG("Debug", TextColor.GRAY),
    INFO("Info", TextColor.AQUA),
    WARN("Warn", TextColor.YELLOW),
    ERROR("Error", TextColor.RED),
    FATAL("Fatal", TextColor.RED);

    private final String displayName;
    private final TextColor color;

    LogLevel(String displayName, TextColor color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TextColor getColor() {
        return color;
    }
}
