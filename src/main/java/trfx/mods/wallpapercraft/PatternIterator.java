package trfx.mods.wallpapercraft;

import trfx.mods.wallpapercraft.autogen.pattern.Pattern;

public class PatternIterator {
    @FunctionalInterface
    public interface Action {
        void execute(Pattern pattern, String variant, Pattern.ModelType modelType);
    }

    public static void iteratePatterns(Action action) {
        for (Pattern pattern : WallpaperCraft.patterns.values()) {
            for (String variant : pattern.getVariantsDefinition().getVariants()) {
                for (Pattern.ModelType modelType : pattern.getModelTypes()) {
                    action.execute(pattern, variant, modelType);
                }
            }
        }
    }
}
