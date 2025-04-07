package com.github.rotlug.glebafarmland.mixin;

import com.github.rotlug.glebafarmland.Glebafarmland;
import com.github.rotlug.glebafarmland.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.init.ModTags;

@Mixin(CropBlock.class)
public class CropBlockMixin {
    @Inject(method = "performBonemeal", at = @At("HEAD"), cancellable = true)
    private void preventBonemealOutOfSeason(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (!ModList.get().isLoaded("sereneseasons")) return;

        Season season = SeasonHelper.getSeasonState(level).getSeason();

        boolean valid = switch (season) {
            case SPRING -> state.is(ModTags.Blocks.SPRING_CROPS);
            case SUMMER -> state.is(ModTags.Blocks.SUMMER_CROPS);
            case AUTUMN -> state.is(ModTags.Blocks.AUTUMN_CROPS);
            case WINTER -> state.is(ModTags.Blocks.WINTER_CROPS);
        };

        if (!valid) {
            ci.cancel();
        }
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void cancelGrowthIfFarmlandIsDry(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (Util.isDryWaterable(level, pos.below())) {
            ci.cancel(); // Cancel crop growth tick if dry
        }
    }
}
