package com.evikka;

import com.evikka.config.ModConfig;
import com.evikka.config.ModConfigManager;
import com.evikka.event.ContainerMenuQuickMoveCallback;
import net.fabricmc.api.ModInitializer;

import net.minecraft.world.InteractionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class InventoryControl implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger(ModConstants.MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

        try {
            ModConfig.INSTANCE = ModConfigManager.load(ModConfig.INSTANCE, ModConfig.class);
        } catch (IOException e) {
            LOGGER.error("Cannot load config file!\n{}", e.getLocalizedMessage());
        }

        ContainerMenuQuickMoveCallback.EVENT.register(() -> InteractionResult.FAIL);
	}
}
