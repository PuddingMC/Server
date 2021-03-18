package me.puds.server.api;

import me.puds.server.api.text.TextBuilder;
import me.puds.server.api.text.TextComponent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// TODO: System.setOut
public class Logger {
    private final String name;

    public Logger(String name) {
        this.name = name;
    }

    public void log(LogLevel level, Object message) {
        String finalMessage = message.toString();
        // TODO: Translate in-game colors to ANSI colors

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        TextComponent formatted = TextBuilder.builder()
                .add("(" + timestamp + ")", level.getColor())
                .space()
                .add("(" + name + "/" + level.getDisplayName() + ")", level.getColor())
                .space()
                .reset()
                .add(finalMessage)
                .reset()
                .build();

        System.out.println(formatted.toAnsiString());
    }

    public void debug(Object message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(Object message) {
        log(LogLevel.INFO, message);
    }

    public void warn(Object message) {
        log(LogLevel.WARN, message);
    }

    public void warn(Object message, Throwable throwable) {
        warn(message);
        throwable.printStackTrace();
    }

    public void error(Object message) {
        log(LogLevel.ERROR, message);
    }

    public void error(Object message, Throwable throwable) {
        error(message);
        throwable.printStackTrace();
    }

    public void fatal(Object message) {
        log(LogLevel.FATAL, message);
    }

    public void fatal(Object message, Throwable throwable) {
        fatal(message);
        throwable.printStackTrace();
    }

    public String getName() {
        return name;
    }
}
