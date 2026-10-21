package com.evikka;

import net.minecraft.resources.Identifier;

public class ModConstants {
    public static final String MOD_ID = "inventorycontrol";
    public static final Identifier INVENTORY_SLOT_TEXTURE = id("textures/gui/inventory_slot.png");

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
