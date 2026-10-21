package com.evikka.client.mixin;

import com.evikka.InventoryControl;
import com.evikka.ModConstants;
import com.evikka.ModUtils;
import com.evikka.config.ModConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin<T extends AbstractContainerMenu> {

    @Final
    @Shadow
    protected T menu;

    @Inject(method = "extractContents",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;extractLabels(Lnet/minecraft/client/gui/GuiGraphicsExtractor;II)V"))
    void drawInventoryBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
        // creative mode inventory
        if (isCreativeMode()) return;

        boolean renderBackground = true;

        var endInv = menu.slots.size() - 1;
        if (menu.getClass().isAssignableFrom(InventoryMenu.class)) {
            endInv--; // offhand slot
        }
        var startInv = endInv - 8 - 9 * ModConfig.INSTANCE.inventoryRows;
        int xMod = menu.slots.get(startInv).x - 2, yMod = 6;

        for (ModConfig.MenuAdjustments menuAdj : ModConfig.INSTANCE.menuAdjustments) {
            var menuClass = ModUtils.findClass(menuAdj.menuClassPath());
            if (menuClass.isPresent() && menu.getClass().isAssignableFrom(menuClass.get())) {
                yMod = menuAdj.modY();
                renderBackground = menuAdj.renderBackground();
            }
        }

        int x0 = menu.slots.get(startInv).x - xMod;
        int y0 = menu.slots.get(startInv).y - yMod;
        int x1 = menu.slots.get(endInv).x + 18 + xMod / 2;
        int y1 = menu.slots.get(endInv).y + 18 + 3;

        if (renderBackground) {
            graphics.fill(x0, y0, x1, y1, 0xFFC6C6C6);
            graphics.fill(x0 - 1, y0, x0 + 1, menu.slots.get(endInv - 8).y + 18 + 3, 0xFFFFFFFF);
            graphics.fill(x1, y0, x1 + 2, y1, 0xFF555555);
//        graphics.outline(x0, y0,x1 - x0, y1 - y0, 0xFF000000);
        }
    }

    @Inject(method = "extractContents",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;extractSlotHighlightBack(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V"))
    void drawInventorySlots(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
        // creative mode inventory
        if (isCreativeMode()) return;

        List<Integer> doNotRender = new ArrayList<>(List.of(0));
        for (ModConfig.MenuAdjustments menuAdj : ModConfig.INSTANCE.menuAdjustments) {
            var menuClass = ModUtils.findClass(menuAdj.menuClassPath());
            if (menuClass.isPresent() && menu.getClass().isAssignableFrom(menuClass.get()))
                doNotRender.addAll(menuAdj.doNotRender());
        }

        for (var slot : menu.slots) {
            if (doNotRender.contains(slot.index)) continue;
            int x = slot.x - 1;
            int y = slot.y - 1;
            graphics.blit(RenderPipelines.GUI_TEXTURED, ModConstants.INVENTORY_SLOT_TEXTURE,
                    x, y,
                    0, 0,
                    18, 18,
                    18, 18);
        }
    }

    @Unique
    boolean isCreativeMode() {
        return menu.getClass().isAssignableFrom(CreativeModeInventoryScreen.ItemPickerMenu.class);
    }
}
