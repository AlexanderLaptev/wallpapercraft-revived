package trfx.mods.wallpapercraft.autogen.variant;

import trfx.mods.wallpapercraft.util.ResourceHelper;
import trfx.mods.wallpapercraft.util.csv.CsvTable;

import java.util.HashMap;
import java.util.Map;

public class VariantListCache {
    private static final Map<String, VariantList> CACHE = new HashMap<>();

    public static VariantList getVariantList(String name) {
        return CACHE.computeIfAbsent(name, key -> loadVariantList(name));
    }

    private static VariantList loadVariantList(String name) {
        String csv = ResourceHelper.getResourceAsString("/autogen/variants/" + name + ".csv");
        return new VariantList(new CsvTable(csv));
    }
}
