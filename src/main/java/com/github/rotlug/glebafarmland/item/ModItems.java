package com.github.rotlug.glebafarmland.item;

import com.github.rotlug.glebafarmland.Glebafarmland;
import com.github.rotlug.glebafarmland.item.wateringCan.WateringCanItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Glebafarmland.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static final DeferredItem<Item> COPPER_WATERING_CAN = ITEMS.register("copper_watering_can",
            () -> new WateringCanItem(new Item.Properties().rarity(Rarity.COMMON).durability(12), 60, 18, 0));

    public static final DeferredItem<Item> IRON_WATERING_CAN = ITEMS.register("iron_watering_can",
            () -> new WateringCanItem(new Item.Properties().rarity(Rarity.COMMON).durability(24), 45, 15, 1));

    public static final DeferredItem<Item> GOLD_WATERING_CAN = ITEMS.register("gold_watering_can",
            () -> new WateringCanItem(new Item.Properties().rarity(Rarity.COMMON).durability(54), 45, 12, 2));

    public static final DeferredItem<Item> DIAMOND_WATERING_CAN = ITEMS.register("diamond_watering_can",
            () -> new WateringCanItem(new Item.Properties().rarity(Rarity.COMMON).durability(90), 40, 12, 3));

    public static final DeferredItem<Item> NETHERITE_WATERING_CAN = ITEMS.register("netherite_watering_can",
            () -> new WateringCanItem(new Item.Properties().rarity(Rarity.RARE).durability(120), 40, 5, 4));

}