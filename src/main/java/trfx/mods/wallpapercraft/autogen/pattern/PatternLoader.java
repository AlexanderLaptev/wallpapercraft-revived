package trfx.mods.wallpapercraft.autogen.pattern;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        Map<String, Pattern> patterns = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            var pattern = Pattern.fromJson(entry.getKey(), entry.getValue().getAsJsonObject());
            patterns.put(pattern.getName(), pattern);
            WallpaperCraft.LOGGER.debug("Loaded pattern: {}", pattern.getName());
        }

        return Collections.unmodifiableMap(patterns);
    }
}
