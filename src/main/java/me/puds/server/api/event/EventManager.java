package me.puds.server.api.event;

import me.puds.server.api.protocol.Connection;
import me.puds.server.api.protocol.Packet;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class EventManager {
    private final Map<Class<? extends Listenable>, List<HandlerObject>> handlers = new HashMap<>();

    public void registerListener(EventListener listener) {
        Class<?> clazz = listener.getClass();
        while (clazz != Object.class) {
            for (Method method : clazz.getDeclaredMethods()) {
                int parameterCount = method.getParameterCount();
                if (!method.isAnnotationPresent(EventHandler.class) || !List.of(1, 2).contains(parameterCount)) {
                    continue;
                }

                Class<?> type = method.getParameterTypes()[0];
                if (!type.isAssignableFrom(Listenable.class)) {
                    continue;
                }

                if (parameterCount == 2) {
                    boolean isPacket = type.isAssignableFrom(Packet.class);
                    boolean isConnection = method.getParameterTypes()[2].isAssignableFrom(Connection.class);
                    if (!isPacket || !isConnection) {
                        continue;
                    }
                }

                Class<? extends Listenable> eventType = (Class<? extends Listenable>) type;
                EventHandler handler = method.getAnnotation(EventHandler.class);

                HandlerObject handlerObject = new HandlerObject(listener, handler, method, parameterCount == 2);
                handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handlerObject);
            }
            clazz = clazz.getSuperclass();
        }
    }

    public <T extends Listenable> T callEvent(Class<T> clazz, T event, Connection connection) {
        List<HandlerObject> handlers = this.handlers.get(clazz);

        Comparator<HandlerObject> comparator = Comparator.comparing(h -> h.getHandler().priority());
        List<HandlerObject> sortedHandlers = handlers.stream().sorted(comparator).collect(Collectors.toList());

        for (HandlerObject handler : sortedHandlers) {
            if (event.isCanceled() && handler.getHandler().ignoreCanceled()) {
                continue;
            }

            try {
                if (connection != null && handler.isIncludeConnection()) {
                    handler.getMethod().invoke(handler.getListener(), event, connection);
                } else {
                    handler.getMethod().invoke(handler.getListener(), event);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: More proper error handling
            }
        }

        return event;
    }

    public <T extends Listenable> T callEvent(Class<T> clazz, T event) {
        return callEvent(clazz, event, null);
    }
}
