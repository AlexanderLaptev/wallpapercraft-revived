package trfx.mods.wallpapercraft.autogen.pattern;

import com.google.gson.annotations.SerializedName;
import trfx.mods.wallpapercraft.autogen.variant.Variant;

public class Pattern {
    public enum Type {
        @SerializedName("stone") STONE,
        @SerializedName("lamp") LAMP,
        @SerializedName("wool") WOOL,
        @SerializedName("carpet") CARPET,
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
