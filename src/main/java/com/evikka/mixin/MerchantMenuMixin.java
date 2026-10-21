package com.evikka.mixin;

import com.evikka.config.ModConfig;
import net.minecraft.world.inventory.MerchantMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MerchantMenu.class)
public class MerchantMenuMixin {
    @ModifyConstant(method = "moveFromInventoryToPaymentSlot", constant = @Constant(intValue = 39))
    int setMaxInvSlots(int orig) {
        return ModConfig.INSTANCE.inventoryRows * 9 + 3;
    }

}
