package trfx.mods.wallpapercraft.autogen.pattern;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import trfx.mods.wallpapercraft.WallpaperCraft;
import trfx.mods.wallpapercraft.util.ResourceHelper;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class PatternLoader {
    private static final String JSON_PATH = "/autogen/patterns.json";

    public static Map<String, Pattern> load() {
        WallpaperCraft.LOGGER.debug("Loading patterns");
        String json = ResourceHelper.getResourceAsString(JSON_PATH);
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(json, JsonArray.class);

        Map<String, Pattern> patterns = new LinkedHashMap<>();
        for (JsonElement element : jsonArray) {
            var pattern = Pattern.fromJson(element.getAsJsonObject());
            patterns.put(pattern.getName(), pattern);
            WallpaperCraft.LOGGER.debug("Loaded pattern: {}", pattern.getName());
        }

        return Collections.unmodifiableMap(patterns);
    }
}
