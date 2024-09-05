package trfx.mods.wallpapercraft;

import trfx.mods.wallpapercraft.autogen.pattern.Pattern;
import trfx.mods.wallpapercraft.autogen.variant.VariantList;
import trfx.mods.wallpapercraft.autogen.variant.VariantListCache;

public class PatternIterator {
    @FunctionalInterface
    public interface Action {
        void execute(Pattern pattern, VariantList.Variant variant, Pattern.ModelType modelType);
    }

    public static void iteratePatterns(Action action) {
        for (Pattern pattern : WallpaperCraft.patterns.values()) {
            VariantList list = VariantListCache.getVariantList(pattern.getVariantListName());
            for (VariantList.Variant variant : list.getVariants().values()) {
                for (Pattern.ModelType modelType : pattern.getModelTypes()) {
                    action.execute(pattern, variant, modelType);
                }
            }
        }
    }
}
