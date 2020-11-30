package me.puds.server.server.tick;

import java.util.ArrayList;
import java.util.List;

public class ComponentManager {
    private final List<TickComponent> components = new ArrayList<>();

    public void add(TickComponent component) {
        components.add(component);
        component.start();
    }

    public void tickAll() {
        for (TickComponent component : components) {
            component.tick();
        }
    }

    public void stopAll() {
        for (TickComponent component : components) {
            component.stop();
            components.remove(component);
        }
    }
}
