package trfx.mods.wallpapercraft.init;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import trfx.mods.wallpapercraft.PatternIterator;
import trfx.mods.wallpapercraft.WallpaperCraft;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;
import trfx.mods.wallpapercraft.block.WallpaperBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreativeTabFiller {
    public static void fillBlocksTab(NonNullList<ItemStack> items) {
        var map = new HashMap<Pattern.ModelType, List<Block>>();
        PatternIterator.iteratePatterns((pattern, variant, modelType) -> {
            map.computeIfAbsent(modelType, key -> new ArrayList<>())
                    .add(getBlock(pattern, variant, modelType));
        });

        for (Block block : map.get(Pattern.ModelType.CUBE)) { items.add(new ItemStack(block)); }
        for (Block block : map.get(Pattern.ModelType.CARPET)) { items.add(new ItemStack(block)); }
    }

    private static Block getBlock(Pattern pattern, String variant, Pattern.ModelType modelType) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(
                WallpaperCraft.MOD_ID,
                WallpaperBlock.getRegistryName(pattern, variant, modelType)
        ));
    }
}
