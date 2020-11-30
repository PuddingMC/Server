package me.puds.server.server.tick;

public interface TickComponent {
    void start();
    void stop();

    void tick();
}
