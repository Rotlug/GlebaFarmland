package com.github.rotlug.glebafarmland.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.Event;

// Event sent by the server when a watering can pours water
public class WateringCanPourEvent extends Event {
    public ServerLevel serverLevel;
    public ServerPlayer player;
    public ItemStack stack;
    public BlockPos pos;
    public BlockState state;
    public boolean isSuper;



    public WateringCanPourEvent(ServerLevel level, ServerPlayer player, ItemStack stack, BlockPos pos, BlockState state, boolean isSuper) {
        this.serverLevel = level;
        this.player = player;
        this.stack = stack;
        this.pos = pos;
        this.state = state;
        this.isSuper = isSuper;
    }
}