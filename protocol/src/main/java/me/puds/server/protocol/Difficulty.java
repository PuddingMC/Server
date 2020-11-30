package me.puds.server.protocol;

import java.util.Arrays;

public enum Difficulty {
    PEACEFUL(0),
    EASY(1),
    NORMAL(2),
    HARD(3);

    private final int value;

    Difficulty(int value) {
        this.value = value;
    }

    public static Difficulty of(int value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue() == value)
                .findFirst()
                .orElse(values()[0]);
    }

    public int getValue() {
        return value;
    }
}
