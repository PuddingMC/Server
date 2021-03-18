package me.puds.server.api.event;

public interface Listenable {
    boolean isCanceled();
    void setCanceled(boolean canceled);
}
