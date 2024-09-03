package trfx.mods.wallpapercraft.core.variant;

import trfx.mods.wallpapercraft.core.csv.CsvTable;
import trfx.mods.wallpapercraft.core.util.ResourceHelper;

import java.util.HashMap;
import java.util.Map;

public class VariantCache {
    private static final String VARIANTS_DIR_PATH = "/autogen/variants/";

    private static final Map<String, Variant> CACHE = new HashMap<>();

    public static Variant getVariant(String name) {
        return CACHE.computeIfAbsent(name, key -> loadVariant(name));
    }

    private static Variant loadVariant(String name) {
        String path = VARIANTS_DIR_PATH + name + ".csv";
        String csv = ResourceHelper.getResourceAsString(path);
        CsvTable table = new CsvTable(csv);
        return new Variant(table);
    }
}
