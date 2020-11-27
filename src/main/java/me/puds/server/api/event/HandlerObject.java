package me.puds.server.api.event;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class HandlerObject {
    private final EventListener listener;
    private final EventHandler handler;
    private final Method method;
    private final boolean includeConnection;
}
