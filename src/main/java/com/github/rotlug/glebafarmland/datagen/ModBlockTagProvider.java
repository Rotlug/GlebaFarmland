package com.github.rotlug.glebafarmland.datagen;

import com.github.rotlug.glebafarmland.Glebafarmland;
import com.github.rotlug.glebafarmland.block.ModBlocks;
import com.github.rotlug.glebafarmland.tag.DewDropBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends net.neoforged.neoforge.common.data.BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable net.neoforged.neoforge.common.data.ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Glebafarmland.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(DewDropBlockTags.WATERABLE)
                .add(ModBlocks.FARMLAND.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.FARMLAND.get());
        tag(BlockTags.BIG_DRIPLEAF_PLACEABLE).add(ModBlocks.FARMLAND.get());
    }
}
