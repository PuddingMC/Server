package me.puds.server.api.protocol;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum ChatPosition {
    CHAT(0),
    SYSTEM_MESSAGE(1),
    GAME_INFO(2);

    private final int value;

    public static ChatPosition of(int value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue() == value)
                .findFirst()
                .orElse(values()[0]);
    }
}
