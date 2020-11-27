package me.puds.server.api;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// TODO: System.setOut
@Data
public class Logger {
    private final String name;

    public void log(LogLevel level, Object message) {
        String finalMessage = message.toString();
        // TODO: Translate in-game colors to ANSI colors

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String formatted = "\u001b[" + level.getColor().getAnsi() + "m" +
                "(" + timestamp + ") " +
                "(" + name + "/" + level.getDisplayName() + ") " +
                "\u001b[0m" + finalMessage;

        System.out.println(formatted);
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
}
