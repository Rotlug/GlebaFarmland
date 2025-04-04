package com.github.rotlug.glebafarmland.block;


import com.github.rotlug.glebafarmland.Config;
import com.github.rotlug.glebafarmland.util.RNG;
import com.github.rotlug.glebafarmland.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;


// Override vanilla Farmland
public class CustomFarmland extends FarmBlock {
    public CustomFarmland(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!Config.noRandomTick) {
            super.randomTick(state, level, pos, random);
        }
    }

    @Override
    protected @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.@NotNull Builder params) {
        return Collections.singletonList(new ItemStack(Blocks.DIRT));
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(@NotNull BlockState state, UseOnContext context, @NotNull ItemAbility itemAbility, boolean simulate) {
        ItemStack itemStack = context.getItemInHand();

        if (!itemStack.canPerformAction(itemAbility)) {
            return null;
        }

        if (ItemAbilities.SHOVEL_FLATTEN == itemAbility && Config.shovelReverting) {
            return Blocks.DIRT.defaultBlockState();
        }

        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }


    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (Config.rainWatering || Config.dailyReset) {

            level.scheduleTick(pos, this, 10);

            if (level.isRainingAt(pos.above()) && Config.rainWatering) {
                Util.setMoist(level, pos);
            }

            if (!Config.dailyReset || !level.getGameRules().getRule(GameRules.RULE_DAYLIGHT).get()) {
                return;
            }
            long dayTime = level.getDayTime() % 24000;
            // check before rain
            if (dayTime >= Config.dailyTimeMin && dayTime < Config.dailyTimeMin + 10) {
                if (!Util.isMoistWaterable(level, pos)) {
                    if (RNG.mc_ihundo(random, Config.dailyDecayChance)) {
                        level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
                    }
                } else {
                    if (RNG.mc_ihundo(random, Config.dailyDryChance)) {
                        Util.setDry(level, pos);
                    }

                }
            }

        }

        if (!Config.sturdyFarmland) {
            super.tick(state, level, pos, random);
        }

    }

    @Override
    public void onBlockStateChange(LevelReader level, @NotNull BlockPos pos, @NotNull BlockState oldState, @NotNull BlockState newState) {
        if (!level.isClientSide()) {
            if (Config.rainWatering || Config.dailyReset) {
                ((ServerLevel) level).scheduleTick(pos, this, 10); // prevent rescheduling
            }
        }
        super.onBlockStateChange(level, pos, oldState, newState);
    }
}
