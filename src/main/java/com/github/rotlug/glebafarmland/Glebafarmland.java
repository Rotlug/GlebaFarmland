package com.github.rotlug.glebafarmland;

import com.github.rotlug.glebafarmland.block.FarmlandEventHandler;
import com.github.rotlug.glebafarmland.block.ModBlocks;
import com.github.rotlug.glebafarmland.item.ModItems;
import com.github.rotlug.glebafarmland.item.wateringCan.WateringCanEventsHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

@Mod(Glebafarmland.MODID)
public class Glebafarmland {
    public static final String MODID = "glebafarmland";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Glebafarmland(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(FarmlandEventHandler.class);
        NeoForge.EVENT_BUS.register(WateringCanEventsHandler.class);

        modEventBus.addListener(this::addCreative);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }

    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModItems.COPPER_WATERING_CAN);
            event.accept(ModItems.IRON_WATERING_CAN);
            event.accept(ModItems.GOLD_WATERING_CAN);
            event.accept(ModItems.DIAMOND_WATERING_CAN);
            event.accept(ModItems.NETHERITE_WATERING_CAN);
        }
    }
}
