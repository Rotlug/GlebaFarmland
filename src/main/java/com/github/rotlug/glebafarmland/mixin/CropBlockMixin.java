package com.github.rotlug.glebafarmland.mixin;

import com.github.rotlug.glebafarmland.Glebafarmland;
import com.github.rotlug.glebafarmland.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CropBlock.class)
public class CropBlockMixin {
    @Inject(method = "performBonemeal", at = @At("HEAD"), cancellable = true)
    private void preventBonemealOutOfSeason(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (!Util.isRightSeason(level, state)) ci.cancel();
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void cancelGrowthIfFarmlandIsDry(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!Util.isRightSeason(level, state)) ci.cancel();

        if (Util.isDryWaterable(level, pos.below())) {
            ci.cancel(); // Cancel crop growth tick if dry
        }
    }
}
