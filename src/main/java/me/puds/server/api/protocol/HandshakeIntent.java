package me.puds.server.api.protocol;

import me.puds.server.api.protocol.client.HandshakePacket;

import java.util.Arrays;

public enum HandshakeIntent {
    STATUS(1),
    LOGIN(2);

    private final int value;

    HandshakeIntent(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static HandshakeIntent of(int value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue() == value)
                .findFirst()
                .orElse(values()[0]);
    }
}
