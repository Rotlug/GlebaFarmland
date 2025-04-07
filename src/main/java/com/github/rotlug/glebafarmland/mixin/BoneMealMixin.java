package com.github.rotlug.glebafarmland.mixin;

import com.github.rotlug.glebafarmland.Config;
import com.github.rotlug.glebafarmland.block.CustomFarmland;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public abstract class BoneMealMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void preventOnDryFarmland(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (!Config.bonemealRequireWet) {
            return;
        }

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);

        // Check if plant is on farmland
        if (belowState.getBlock() instanceof CustomFarmland customFarmland) {
            // Check moisture level
            if (belowState.getValue(CustomFarmland.MOISTURE) == 0) {
                // Optionally: play fail sound or particles
                if (!level.isClientSide) {
                    Player player = context.getPlayer();
                    if (player != null) {
                        player.displayClientMessage(Component.literal("Too dry to grow!"), true);
                    }
                }

                // Cancel the bonemeal usage
                cir.setReturnValue(InteractionResult.FAIL);
            }
        }
    }
}
