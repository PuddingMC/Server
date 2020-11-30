package me.puds.server.protocol;

import java.util.Arrays;

public enum GameMode {
    NONE(-1),
    SURVIVAL(0),
    CREATIVE(2),
    ADVENTURE(3),
    SPECTATOR(4);

    private final int value;

    GameMode(int value) {
        this.value = value;
    }

    public static GameMode of(int value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue() == value)
                .findFirst()
                .orElse(values()[0]);
    }

    public int getValue() {
        return value;
    }
}
