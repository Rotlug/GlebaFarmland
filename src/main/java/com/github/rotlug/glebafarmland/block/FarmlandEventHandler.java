package com.github.rotlug.glebafarmland.block;

import com.github.rotlug.glebafarmland.Glebafarmland;
import com.github.rotlug.glebafarmland.ModEventSubscriber;
import com.github.rotlug.glebafarmland.tag.DewDropBlockTags;
import com.github.rotlug.glebafarmland.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FarmlandEventHandler {
    @SubscribeEvent
    public static void onFarmlandTrampleEvent(BlockEvent.FarmlandTrampleEvent event) {
        if (ModEventSubscriber.noTrample) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onSplashWaterBottleHitEvent(ProjectileImpactEvent event) {
        if (!ModEventSubscriber.splashPotionWatering) {
            return;
        }

        if (event.getProjectile() instanceof ThrownPotion) {
            if (event.getRayTraceResult().getType() == HitResult.Type.BLOCK) {
                Level level = (Objects.requireNonNull(event.getProjectile().getOwner()).level());
                if (level.isClientSide()) {
                    return;
                }
                BlockPos pos = BlockPos.containing(event.getRayTraceResult().getLocation());
                Glebafarmland.LOGGER.info(String.valueOf(pos));
                if (ModEventSubscriber.splashWaterArea == 0) {
                    if (level.getBlockState(pos).is(DewDropBlockTags.WATERABLE)) {
                        Util.setMoist(((ServerLevel) level), pos);
                    }
                } else {
                    BlockPos.withinManhattanStream(pos, ModEventSubscriber.splashWaterArea, 0, ModEventSubscriber.splashWaterArea).forEach(blockPos -> {
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

        if (!ModEventSubscriber.bucketWatering &&! ModEventSubscriber.bottleWatering) {
            return;
        }

        ServerLevel level = (ServerLevel) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        if (!state.is(DewDropBlockTags.WATERABLE)) {
            if (state.is(BlockTags.CROPS) && level.getBlockState(pos.below()).is(DewDropBlockTags.WATERABLE)) {
                pos = pos.below();
                state = level.getBlockState(pos);
            } else {
                return;
            }
        }
        Player player = event.getEntity();
        ItemStack stack = player.getItemInHand(event.getHand());


        if (ModEventSubscriber.bucketWatering && stack.is(Items.WATER_BUCKET)) {
            if (!player.isCreative()) {
                player.setItemInHand(event.getHand(), new ItemStack(Items.BUCKET));
            }
        } else if (ModEventSubscriber.bottleWatering && stack.is(Items.POTION)) {
            if (!player.isCreative()) {
                player.setItemInHand(event.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            }
        } else {
            return;
        }
        Util.setMoist(level, pos);
    }
}
