package me.puds.server.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.puds.server.api.text.TextColor;

@RequiredArgsConstructor
@Getter
public enum LogLevel {
    DEBUG("Debug", TextColor.GRAY),
    INFO("Info", TextColor.AQUA),
    WARN("Warn", TextColor.YELLOW),
    ERROR("Error", TextColor.RED),
    FATAL("Fatal", TextColor.RED);

    private final String displayName;
    private final TextColor color;
}
