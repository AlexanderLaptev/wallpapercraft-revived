package trfx.mods.wallpapercraft.autogen.variant;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import trfx.mods.wallpapercraft.WallpaperCraft;
import trfx.mods.wallpapercraft.util.ResourceHelper;

import java.util.LinkedHashMap;
import java.util.Map;

public class VariantsDefinitionCache {
    private static final String JSON_PATH = "/autogen/variants.json";

    private static Map<String, VariantsDefinition> CACHE;

    public static VariantsDefinition getForName(String name) {
        if (CACHE == null) {
            loadVariantsJson();
        }
        return CACHE.get(name);
    }

    private static void loadVariantsJson() {
        WallpaperCraft.LOGGER.debug("Caching variant definitions");
        String json = ResourceHelper.getResourceAsString(JSON_PATH);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        CACHE = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            VariantsDefinition variantsDefinition = VariantsDefinition.fromJson(entry.getKey(), entry.getValue().getAsJsonArray());
            CACHE.put(variantsDefinition.getId(), variantsDefinition);
            WallpaperCraft.LOGGER.debug("Loaded variant definition: {}", variantsDefinition.getId());
        }
    }

//    private static Variants loadVariantList(String name) {
//        WallpaperCraft.LOGGER.debug("Loading variant '{}'", name);
//        return new Variants(new CsvTable(csv));
//    }
}
