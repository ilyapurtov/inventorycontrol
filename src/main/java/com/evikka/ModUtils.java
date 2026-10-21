package com.evikka;

import java.util.Optional;

public class ModUtils {
    public static Optional<Class<?>> findClass(String className) {
        try {
            Class<?> found = Class.forName(className);
            return Optional.of(found);
        } catch(ClassNotFoundException e) {
            InventoryControl.LOGGER.error("Cannot find class {}", className);
        }
        return Optional.empty();
    }
}
