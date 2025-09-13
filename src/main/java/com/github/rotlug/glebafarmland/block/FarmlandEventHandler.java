package com.github.rotlug.glebafarmland.block;

import com.github.rotlug.glebafarmland.Config;
import com.github.rotlug.glebafarmland.Glebafarmland;
import com.github.rotlug.glebafarmland.tag.DewDropBlockTags;
import com.github.rotlug.glebafarmland.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.Objects;

public class FarmlandEventHandler {
    @SubscribeEvent
    public static void onFarmlandTrampleEvent(BlockEvent.FarmlandTrampleEvent event) {
        if (Config.noTrample) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onSplashWaterBottleHitEvent(ProjectileImpactEvent event) {
        if (!Config.splashPotionWatering) {
            return;
        }

        if (event.getProjectile() instanceof ThrownPotion) {
            if (event.getRayTraceResult().getType() == HitResult.Type.BLOCK) {
                Entity entity = event.getProjectile().getOwner();
                if (entity == null) return;
                Level level = entity.level();

                if (level.isClientSide()) {
                    return;
                }
                BlockPos pos = BlockPos.containing(event.getRayTraceResult().getLocation());
                if (Config.splashWaterArea == 0) {
                    if (level.getBlockState(pos).is(DewDropBlockTags.WATERABLE)) {
                        Util.setMoist(((ServerLevel) level), pos);
                    }
                } else {
                    BlockPos.withinManhattanStream(pos, Config.splashWaterArea, 0, Config.splashWaterArea).forEach(blockPos -> {
                        BlockState state = level.getBlockState(blockPos);
                        if (state.is(DewDropBlockTags.WATERABLE)) {
                            Util.setMoist(((ServerLevel) level), blockPos);
                        } else if (level.getBlockState(blockPos.below()).is(DewDropBlockTags.WATERABLE)) {
                            Util.setMoist(((ServerLevel) level), blockPos.below());
                        }
                    });
                }
            }
        }
    }

    @SubscribeEvent
    public static void interactBlockEvent(PlayerInteractEvent.RightClickItem event) {
        if (event.getLevel().isClientSide) {
            return;
        }

        if (!Config.bucketWatering &&! Config.bottleWatering) {
            return;
        }

        ServerLevel level = (ServerLevel) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        if (!state.is(DewDropBlockTags.WATERABLE)) {
            if (state.is(BlockTags.CROPS) && level.getBlockState(pos.below()).is(DewDropBlockTags.WATERABLE)) {
                pos = pos.below();
//                state = level.getBlockState(pos);
            } else {
                return;
            }
        }
        Player player = event.getEntity();
        ItemStack stack = player.getItemInHand(event.getHand());


        if (Config.bucketWatering && stack.is(Items.WATER_BUCKET)) {
            if (!player.isCreative()) {
                player.setItemInHand(event.getHand(), new ItemStack(Items.BUCKET));
            }
        } else if (Config.bottleWatering && stack.is(Items.POTION)) {
            if (!player.isCreative()) {
                player.setItemInHand(event.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            }
        } else {
            return;
        }
        Util.setMoist(level, pos);
    }

    /*
    Replace Regular farmland with modified farmland
     */
    @SubscribeEvent
    public static void blockPlaceEvent(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().is(Blocks.FARMLAND)) {
            event.getLevel().setBlock(event.getPos(), ModBlocks.FARMLAND.get().defaultBlockState(), 3);
        }
    }

    @SubscribeEvent
    public static void blockToolModEvent(BlockEvent.BlockToolModificationEvent event) {
        if (event.getFinalState().is(Blocks.FARMLAND)) {
            event.setFinalState(ModBlocks.FARMLAND.get().defaultBlockState());
        }
    }
}
