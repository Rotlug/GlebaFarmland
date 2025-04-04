package com.github.rotlug.glebafarmland.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.Event;

public class WateringCanSuperEvent extends Event {
    public ServerLevel serverLevel;
    public ServerPlayer player;
    public ItemStack stack;
    public AABB area;
    public int superLevel;

    public WateringCanSuperEvent(ServerLevel serverLevel, ServerPlayer player, ItemStack stack, AABB area, int superLevel) {
        this.serverLevel = serverLevel;
        this.player = player;
        this.stack = stack;
        this.area = area;
        this.superLevel = superLevel;
    }
}