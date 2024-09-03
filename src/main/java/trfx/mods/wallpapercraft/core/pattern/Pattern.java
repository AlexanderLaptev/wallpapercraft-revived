package trfx.mods.wallpapercraft.core.pattern;

import com.google.gson.annotations.SerializedName;
import trfx.mods.wallpapercraft.core.variant.Variant;

import java.util.Collections;
import java.util.Set;

public class Pattern {
    public enum Type { // New blocks should match the corresponding vanilla ones as close as possible.
        @SerializedName("stone") STONE,
        @SerializedName("lamp") LAMP,
        @SerializedName("wool") WOOL, // Also generates carpets
        @SerializedName("glass") GLASS,
        @SerializedName("planks") PLANKS
    }

    private final String name;

    private final Type type;

    private final Variant variant;

    public Pattern(String name, Type type, Variant variant) {
        this.name = name;
        this.type = type;
        this.variant = variant;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Variant getVariant() {
        return variant;
    }
}
