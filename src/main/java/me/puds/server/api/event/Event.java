package me.puds.server.api.event;

import lombok.Data;

@Data
public abstract class Event implements Listenable {
    private boolean canceled = false;
}
