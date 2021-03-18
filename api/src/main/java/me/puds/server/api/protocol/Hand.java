package me.puds.server.api.protocol;

import java.util.Arrays;

public enum Hand {
    MAIN_HAND(0),
    OFF_HAND(1);

    private final int value;

    Hand(int value) {
        this.value = value;
    }

    public static Hand of(int value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue() == value)
                .findFirst()
                .orElse(values()[0]);
    }

    public int getValue() {
        return value;
    }
}
