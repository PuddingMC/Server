package me.puds.server.common;

public interface Listenable {
    boolean isCanceled();
    void setCanceled(boolean canceled);
}
