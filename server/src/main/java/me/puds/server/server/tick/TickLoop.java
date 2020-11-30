package me.puds.server.server.tick;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TickLoop {
    private boolean running = false;

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private ScheduledFuture<?> future = null;

    public void start() {
        TickRunnable runnable = new TickRunnable();
        future = executor.scheduleAtFixedRate(runnable, 0, 50, TimeUnit.MILLISECONDS);
        running = true;
    }

    public void stop(boolean gracefully) {
        future.cancel(!gracefully);
        future = null;
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
