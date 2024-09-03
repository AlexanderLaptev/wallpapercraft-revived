package trfx.mods.wallpapercraft.core.pattern;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import trfx.mods.wallpapercraft.core.util.ResourceHelper;
import trfx.mods.wallpapercraft.core.variant.VariantCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PatternLoader {
    private static final String PATTERNS_JSON_PATH = "/autogen/patterns.json";

    public static List<Pattern> load() {
        String patternsJson = ResourceHelper.getResourceAsString(PATTERNS_JSON_PATH);
        return parseJson(patternsJson);
    }

    private static List<Pattern> parseJson(String patternsJson) {
        List<Pattern> result = new ArrayList<>(32);
        Gson gson = new Gson();
        JsonObject topLevel = gson.fromJson(patternsJson, JsonObject.class);
        for (Map.Entry<String, JsonElement> entry : topLevel.entrySet()) {
            JsonObject patternObject = entry.getValue().getAsJsonObject();
            String name = entry.getKey();
            Pattern.Type type = gson.fromJson(patternObject.get("type"), Pattern.Type.class);
            String variantName = patternObject.get("variant").getAsString();
            result.add(new Pattern(name, type, VariantCache.getVariant(variantName)));
        }
        return result;
    }
}
