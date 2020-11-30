package me.puds.server.api.event;

import me.puds.server.common.Listenable;
import me.puds.server.protocol.Connection;
import me.puds.server.protocol.Packet;

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
                if (!Listenable.class.isAssignableFrom(type)) {
                    continue;
                }

                if (parameterCount == 2) {
                    boolean isPacket = Packet.class.isAssignableFrom(type);
                    boolean isConnection = Connection.class.isAssignableFrom(method.getParameterTypes()[1]);
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

    public <T extends Listenable> T callEvent(Class<? extends Listenable> clazz, T event, Connection connection) {
        List<HandlerObject> handlers = this.handlers.get(clazz);
        if (handlers == null) {
            return event;
        }

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
