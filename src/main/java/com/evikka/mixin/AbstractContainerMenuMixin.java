package com.evikka.mixin;

import com.evikka.config.ModConfig;
import com.evikka.event.ContainerMenuQuickMoveCallback;
import com.evikka.mixin.accessors.InventoryMenuAccessor;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {
    @ModifyConstant(method = "addInventoryExtendedSlots", constant = @Constant(intValue = 3))
    int modifyInventoryRows(int orig) {
//        if (isCreative()) {
//            return orig;
//        }
        return ModConfig.INSTANCE.inventoryRows;
    }

    @ModifyConstant(method = "addStandardInventorySlots", constant = @Constant(intValue = 58))
    int modifyHotbarMargin(int orig) {
//        if (isCreative()) return orig;
        return ModConfig.INSTANCE.inventoryRows * 18 + 4;
    }

    @Inject(method = "doClick", cancellable = true,
            at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/world/inventory/AbstractContainerMenu;quickMoveStack(Lnet/minecraft/world/entity/player/Player;I)Lnet/minecraft/world/item/ItemStack;"))
    void invokeQuickMoveEvent(int slotIndex, int buttonNum, ContainerInput containerInput, Player player, CallbackInfo ci) {
        InteractionResult result = ContainerMenuQuickMoveCallback.EVENT.invoker().interact();
        if (result != InteractionResult.PASS)
            ci.cancel();
    }

    @Unique
    boolean isCreative() {
        if (this.getClass().isAssignableFrom(InventoryMenu.class)) {
            InventoryMenuAccessor inventoryMenu = (InventoryMenuAccessor) this;
            return inventoryMenu.inventorycontrol$getOwner().isCreative();
        }
        return false;
    }
}
