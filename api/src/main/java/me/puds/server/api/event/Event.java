package me.puds.server.api.event;

import me.puds.server.common.Listenable;

public abstract class Event implements Listenable {
    private boolean canceled = false;

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
