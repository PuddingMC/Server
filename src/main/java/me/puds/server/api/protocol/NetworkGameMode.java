package me.puds.server.api.protocol;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum NetworkGameMode {
    NONE(-1),
    SURVIVAL(0),
    CREATIVE(2),
    ADVENTURE(3),
    SPECTATOR(4);

    private final int value;

    public static NetworkGameMode of(int value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue() == value)
                .findFirst()
                .orElse(values()[0]);
    }
}
