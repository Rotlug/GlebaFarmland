package com.github.rotlug.glebafarmland.event;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

public class WateringCanFailEvent extends Event {
    public ServerLevel serverLevel;
    public ServerPlayer player;
    public ItemStack stack;

    public WateringCanFailEvent(ServerLevel serverLevel, ServerPlayer player, ItemStack stack) {
        this.serverLevel = serverLevel;
        this.player = player;
        this.stack = stack;
    }
}