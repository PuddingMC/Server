package me.puds.server.tick;

public interface TickComponent {
    void start();
    void stop();

    void tick();
}
