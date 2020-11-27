package me.puds.server.tick;

import me.puds.server.api.Server;

public class TickRunnable implements Runnable {
    private long lastTick = 0;

    private void tick(boolean catchUp) {
        // TODO: Implement a better catch-up system
        if (catchUp && lastTick != 0) {
            long sinceLastTick = System.currentTimeMillis() - lastTick;
            long ticksMissed = (sinceLastTick / 50) - 1;

            if (ticksMissed > 0) {
                Server.getLogger().warn("Trying to catch up! Last server tick took " + sinceLastTick + "ms " +
                        "(or the time of " + (ticksMissed + 1) + " ticks)");

                for (int i = 0; i < ticksMissed; i++) {
                    tick(false);
                }
            }
        }

        lastTick = System.currentTimeMillis();
        Server.getComponentManager().tickAll();
    }

    @Override
    public void run() {
        tick(true);
    }
}
