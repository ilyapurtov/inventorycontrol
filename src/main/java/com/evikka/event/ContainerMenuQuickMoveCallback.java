package com.evikka.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;

/**
 * Callback for quick move event in containers
 */
public interface ContainerMenuQuickMoveCallback {
    Event<ContainerMenuQuickMoveCallback> EVENT = EventFactory.createArrayBacked(ContainerMenuQuickMoveCallback.class,
            (listeners) -> () -> {
                for (ContainerMenuQuickMoveCallback listener : listeners) {
                    InteractionResult result = listener.interact();
                    if (result != InteractionResult.PASS) {
                        return result;
                    }
                }
                return InteractionResult.PASS;
            });

    InteractionResult interact();
}
