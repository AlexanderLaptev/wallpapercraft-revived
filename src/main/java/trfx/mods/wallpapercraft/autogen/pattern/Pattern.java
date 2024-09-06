package trfx.mods.wallpapercraft.autogen.pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Pattern {
    public enum Material {
        @SerializedName("stone") STONE,
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
    private final String variantListName;
    private final Material material;
    private final Set<ModelType> modelTypes;

    public Pattern(
            String name,
            String variantListName,
            Material material,
            Set<ModelType> modelTypes
    ) {
        this.name = name;
        this.variantListName = variantListName;
        this.material = material;
        this.modelTypes = modelTypes;
    }

    public static Pattern fromJson(JsonObject object) {
        String name = object.get("name").getAsString();
        String variantsList = object.get("variants").getAsString();
        Material material = Material.valueOf(object.get("material").getAsString().toUpperCase());

        Set<ModelType> modelTypes = new HashSet<>();
        JsonArray modelTypesArray = object.getAsJsonArray("models");
        for (var element : modelTypesArray) {
            modelTypes.add(ModelType.valueOf(element.getAsString().toUpperCase()));
        }

        return new Pattern(name, variantsList, material, Collections.unmodifiableSet(modelTypes));
    }

    public String getName() { return name; }

    public String getVariantListName() { return variantListName; }

    public Material getMaterial() { return material; }

    public Set<ModelType> getModelTypes() { return modelTypes; }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null || obj.getClass() != this.getClass()) { return false; }
        var that = (Pattern) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.variantListName, that.variantListName) &&
                Objects.equals(this.material, that.material) &&
                Objects.equals(this.modelTypes, that.modelTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, variantListName, material, modelTypes);
    }

    @Override
    public String toString() {
        return "Pattern[" +
                "name=" + name + ", " +
                "variantListName=" + variantListName + ", " +
                "material=" + material + ", " +
                "modelTypes=" + modelTypes + ']';
    }
}
