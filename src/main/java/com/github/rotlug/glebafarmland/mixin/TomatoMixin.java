package com.github.rotlug.glebafarmland.mixin;

import com.github.rotlug.glebafarmland.Glebafarmland;
import com.github.rotlug.glebafarmland.block.CustomFarmland;
import com.github.rotlug.glebafarmland.block.ModBlocks;
import com.github.rotlug.glebafarmland.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.TomatoVineBlock;

@Mixin(TomatoVineBlock.class)
public class TomatoMixin {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void preventRandomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        BlockState below = level.getBlockState(pos.below());

        if (Util.isDryWaterable(level, pos.below())) {
            ci.cancel();
        }
        else if (below.is(vectorwing.farmersdelight.common.registry.ModBlocks.TOMATO_CROP.get())) {
            // Locate the farm block
            BlockPos blockPos = pos.below().below();
            BlockState blockState = level.getBlockState(blockPos);
            int upperCap = 0;

            while (!(blockState.getBlock() instanceof FarmBlock) && upperCap < 10) {
                blockPos = blockPos.below();
                blockState = level.getBlockState(blockPos);
                upperCap++;
            }

            if (Util.isDryWaterable(level, blockPos)) {
                ci.cancel();
            }
        }
    }
}
