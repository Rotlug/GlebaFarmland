package com.github.rotlug.glebafarmland.mixin;

import com.github.rotlug.glebafarmland.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoeItem.class)
public abstract class HoeTillMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void injectCustomFarmland(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK)) {
            if (!level.isClientSide && level.getBlockState(pos.above()).isAir()) {
                level.setBlock(pos, ModBlocks.FARMLAND.get().defaultBlockState(), 11);

                Player player = context.getPlayer();
                level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (player != null) {
                    ItemStack stack = context.getItemInHand();
                    stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
                }

                cir.setReturnValue(InteractionResult.sidedSuccess(false));
            }
        }
    }
}