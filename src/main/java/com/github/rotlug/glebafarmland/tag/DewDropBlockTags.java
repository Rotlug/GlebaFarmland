package com.github.rotlug.glebafarmland.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class DewDropBlockTags {
    // Dew Drop project blocktags
    public static final TagKey<Block> WATERABLE = tag("waterable");

    public static TagKey<Block> tag(String name) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("glebafarmland", name));
    }
}
