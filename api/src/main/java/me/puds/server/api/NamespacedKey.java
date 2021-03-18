package me.puds.server.api;

import java.util.Arrays;

public class NamespacedKey {
    public static final String MINECRAFT = "minecraft";

    private final String namespace;
    private final String key;

    public NamespacedKey(String namespace, String key) {
        this.namespace = namespace;
        this.key = key;
    }

    public NamespacedKey(String key) {
        if (!key.contains(":")) {
            namespace = MINECRAFT;
            this.key = key;
            return;
        }

        String[] split = key.split(":");
        if (split.length <= 0) {
            namespace = MINECRAFT;
            this.key = "";
        } else if (split.length == 1) {
            namespace = split[0];
            this.key = "";
        } else {
            namespace = split[0];
            this.key = String.join(":", Arrays.copyOfRange(split, 1, split.length));
        }
    }

    public static NamespacedKey minecraft(String key) {
        return new NamespacedKey(MINECRAFT, key);
    }

    @Override
    public String toString() {
        return namespace + ":" + key;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getKey() {
        return key;
    }
}
