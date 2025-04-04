package com.github.rotlug.glebafarmland.util;



import com.github.rotlug.glebafarmland.tag.DewDropBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.HitResult;

public final class Util {

    /*  Custom Raycast function that more accurately returns the block or fluid the player is looking at without using any extra datatypes.
    /   Returns null if the player is staring at nothing, as you do.
    */
    public static BlockPos blockHighlightedOrNull(Player player) {
        HitResult hitResult = player.pick(player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE),1.0F,true);
        if (!(hitResult.getType() == HitResult.Type.BLOCK)) {
            return null;
        }
        return BlockPos.containing(hitResult.getLocation().add(hitResult.getLocation().subtract(player.getEyePosition()).normalize().multiply(0.001, 0.001, 0.001)));
    }

    public static boolean isMoistWaterable(ServerLevel level, BlockPos pos) {
       return level.getBlockState(pos).is(DewDropBlockTags.WATERABLE) && level.getBlockState(pos).getValue(BlockStateProperties.MOISTURE) >= 1;
    }

    public static boolean isDryWaterable(ServerLevel level, BlockPos pos) {
        return level.getBlockState(pos).is(DewDropBlockTags.WATERABLE) && level.getBlockState(pos).getValue(BlockStateProperties.MOISTURE) == 0;
    }

    // Calling these methods on blocks that do not have a moisture property like farmland (0-7) will break things.
    public static void setMoist(ServerLevel level, BlockPos pos) {
        level.setBlock(pos, level.getBlockState(pos).setValue(BlockStateProperties.MOISTURE, 7), 3);
    }
    public static void setDry(ServerLevel level, BlockPos pos) {
        level.setBlock(pos, level.getBlockState(pos).setValue(BlockStateProperties.MOISTURE, 0), 3);
    }

}