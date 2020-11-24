package me.puds.server.api;

import java.util.Arrays;

public enum Gamemode {
    NONE(-1),
    SURVIVAL(0),
    CREATIVE(2),
    ADVENTURE(3),
    SPECTATOR(4);

    private final int value;

    Gamemode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Gamemode of(int value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue() == value)
                .findFirst()
                .orElse(values()[0]);
    }
}
