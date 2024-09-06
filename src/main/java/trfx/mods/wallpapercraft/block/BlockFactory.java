package trfx.mods.wallpapercraft.block;

import net.minecraft.world.level.block.Blocks;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;

public class BlockFactory {
    public static WallpaperBlock makeBlock(Pattern pattern, String variant, Pattern.ModelType modelType) {
        if (modelType == Pattern.ModelType.CARPET) {
            return new CarpetWallpaperBlock(
                    pattern,
                    variant,
                    Blocks.WHITE_CARPET,
                    false
            );
        } else {
            return switch (pattern.getMaterial()) {
                case STONE -> new WallpaperBlock(pattern, variant, modelType, Blocks.STONE, false);
                case BRICKS -> new WallpaperBlock(pattern, variant, modelType, Blocks.BRICKS, false);
                case PLANKS -> new WallpaperBlock(pattern, variant, modelType, Blocks.OAK_PLANKS, false);
                case GLASS -> new GlassWallpaperBlock(pattern, variant, modelType, false);
                case WOOL -> new WallpaperBlock(pattern, variant, modelType, Blocks.WHITE_WOOL, false);
                case LAMP -> new WallpaperBlock(pattern, variant, modelType, Blocks.GLOWSTONE, true);
            };
        }
    }
}
