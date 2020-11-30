package me.puds.server.api.event;

import java.lang.reflect.Method;

public class HandlerObject {
    private final EventListener listener;
    private final EventHandler handler;
    private final Method method;
    private final boolean includeConnection;

    public HandlerObject(EventListener listener, EventHandler handler, Method method, boolean includeConnection) {
        this.listener = listener;
        this.handler = handler;
        this.method = method;
        this.includeConnection = includeConnection;
    }

    public EventListener getListener() {
        return listener;
    }

    public EventHandler getHandler() {
        return handler;
    }

    public Method getMethod() {
        return method;
    }

    public boolean isIncludeConnection() {
        return includeConnection;
    }
}
