package me.puds.server.api.protocol;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum HandshakeIntent {
    STATUS(1),
    LOGIN(2);

    private final int value;

    public static HandshakeIntent of(int value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue() == value)
                .findFirst()
                .orElse(values()[0]);
    }
}
