package me.puds.server.api;

import java.util.Arrays;

public enum ChatPosition {
    CHAT(0),
    SYSTEM_MESSAGE(1),
    GAME_INFO(2);

    private final int value;

    ChatPosition(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ChatPosition of(int value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue() == value)
                .findFirst()
                .orElse(values()[0]);
    }
}
