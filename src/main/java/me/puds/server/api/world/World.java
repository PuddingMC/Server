package me.puds.server.api.world;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class World {
    private int dayTime;
    private long totalTime;

    private LocalDateTime firstLoaded;
    private LocalDateTime lastLoaded;

    private boolean raining;
    private boolean thundering;

    public void load() {
        // TODO
    }
}
