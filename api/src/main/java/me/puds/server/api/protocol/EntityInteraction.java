package me.puds.server.api.protocol;

import java.util.Arrays;

public enum EntityInteraction {
    INTERACT(0),
    ATTACK(1),
    INTERACT_AT(2);

    private final int value;

    EntityInteraction(int value) {
        this.value = value;
    }

    public static EntityInteraction of(int value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue() == value)
                .findFirst()
                .orElse(values()[0]);
    }

    public int getValue() {
        return value;
    }
}
