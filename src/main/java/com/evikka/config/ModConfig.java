package com.evikka.config;

import java.util.ArrayList;
import java.util.List;

public class ModConfig {

    public record MenuAdjustments(String menuClassPath, int modY, List<Integer> doNotRender, boolean renderBackground) {
        public MenuAdjustments(String menuClassPath, int modY) {
            this(menuClassPath, modY, new ArrayList<>(List.of(0)), true);
        }
        public MenuAdjustments(String menuClassPath, List<Integer> doNotRender) {
            this(menuClassPath, 6, doNotRender, true);
        }
        public MenuAdjustments(String menuClassPath, boolean renderBackground) {
            this(menuClassPath, 6, new ArrayList<>(List.of(0)), renderBackground);
        }

    }

    public static ModConfig INSTANCE = new ModConfig();

    /**
     * Range 0-3
     */
    public int inventoryRows = 0;

    /**
     * Several menu adjustments
     */
    public List<MenuAdjustments> menuAdjustments = new ArrayList<>(List.of(
            new MenuAdjustments("net.minecraft.world.inventory.CraftingMenu", 12),
            new MenuAdjustments("net.minecraft.world.inventory.ChestMenu", 18),
            new MenuAdjustments("net.minecraft.world.inventory.FurnaceMenu", new ArrayList<>(List.of(2))),
            new MenuAdjustments("net.minecraft.world.inventory.MerchantMenu",
                    6,
                    new ArrayList<>(List.of(2)),
                    false)
    ));
}
