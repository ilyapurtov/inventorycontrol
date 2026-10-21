package com.evikka.mixin;

import com.evikka.config.ModConfig;
import com.evikka.mixin.accessors.InventoryAccessor;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Inventory.class)
public class InventoryMixin {
    @Redirect(
            method = "<init>",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/player/Inventory;items:Lnet/minecraft/core/NonNullList;",
                    opcode = Opcodes.PUTFIELD
            )
    )
    void changeItems(Inventory instance, NonNullList<ItemStack> origItems) {
        ((InventoryAccessor) instance)
                .setItems(NonNullList.withSize(9 + ModConfig.INSTANCE.inventoryRows * 9, ItemStack.EMPTY));
    }
}
