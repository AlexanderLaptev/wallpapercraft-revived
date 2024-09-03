package trfx.mods.wallpapercraft.core.pattern;

import java.util.Set;

public class Pattern {
    public enum Type { // New blocks should match the corresponding vanilla ones as close as possible.
        STONE,
        LAMP,
        WOOL, // Also generates carpets
        GLASS,
        PLANKS
    }

    private String name;

    private Type type;

    private Set<String> variants;
}
