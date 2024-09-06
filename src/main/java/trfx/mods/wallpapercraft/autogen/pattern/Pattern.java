package trfx.mods.wallpapercraft.autogen.pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import trfx.mods.wallpapercraft.autogen.variant.VariantsDefinitionCache;
import trfx.mods.wallpapercraft.autogen.variant.VariantsDefinition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Pattern {
    public enum Material {
        @SerializedName("stone") STONE,
        @SerializedName("bricks") BRICKS,
        @SerializedName("wool") WOOL,
        @SerializedName("lamp") LAMP,
        @SerializedName("glass") GLASS,
        @SerializedName("planks") PLANKS
    }

    public enum ModelType {
        @SerializedName("cube") CUBE(""),
        @SerializedName("carpet") CARPET("carpet");

        private final String suffix;

        ModelType(String suffix) { this.suffix = suffix; }

        public String getSuffix() { return suffix; }
    }

    private final String name;
    private final VariantsDefinition definition;
    private final Material material;
    private final Set<ModelType> modelTypes;

    public Pattern(
            String name,
            VariantsDefinition definition,
            Material material,
            Set<ModelType> modelTypes
    ) {
        this.name = name;
        this.definition = definition;
        this.material = material;
        this.modelTypes = modelTypes;
    }

    public static Pattern fromJson(String name, JsonObject object) {
        VariantsDefinition variantsDefinition = VariantsDefinitionCache.getForName(object.get("variants").getAsString());
        Material material = Material.valueOf(object.get("material").getAsString().toUpperCase());

        Set<ModelType> modelTypes = new HashSet<>();
        JsonArray modelTypesArray = object.getAsJsonArray("models");
        for (var element : modelTypesArray) {
            modelTypes.add(ModelType.valueOf(element.getAsString().toUpperCase()));
        }

        return new Pattern(name, variantsDefinition, material, Collections.unmodifiableSet(modelTypes));
    }

    public String getName() { return name; }

    public VariantsDefinition getVariantsDefinition() { return definition; }

    public Material getMaterial() { return material; }

    public Set<ModelType> getModelTypes() { return modelTypes; }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null || obj.getClass() != this.getClass()) { return false; }
        var that = (Pattern) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.definition, that.definition) &&
                Objects.equals(this.material, that.material) &&
                Objects.equals(this.modelTypes, that.modelTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, definition, material, modelTypes);
    }

    @Override
    public String toString() {
        return "Pattern[" +
                "name=" + name + ", " +
                "variantListName=" + definition + ", " +
                "material=" + material + ", " +
                "modelTypes=" + modelTypes + ']';
    }
}
