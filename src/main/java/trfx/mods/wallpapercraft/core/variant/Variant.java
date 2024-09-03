package trfx.mods.wallpapercraft.core.variant;

import org.apache.commons.lang3.Validate;
import trfx.mods.wallpapercraft.core.csv.CsvTable;

import java.util.*;

public class Variant {
    private final Set<String> internalNames;

    private final Map<String, Map<String, String>> localizedLookup;

    public Variant(CsvTable table) {
        // Validate
        for (int row = 1; row < table.getRowCount(); row++) {
            Validate.notEmpty(table.get(row, 0), "Variant with no internal name found in row %d", row);
        }

        // Fill internal names
        Set<String> names = new HashSet<>();
        for (int row = 1; row < table.getRowCount(); row++) {
            String name = table.get(row, 0);
            Validate.isTrue(names.add(name), "Duplicate of variant '%s' found in row %d", name, row);
        }
        this.internalNames = Collections.unmodifiableSet(names);

        // Fill lookup
        Map<String, Map<String, String>> lookup = new HashMap<>();
        for (int row = 1; row < table.getRowCount(); row++) {
            String internal = table.get(row, 0);
            Map<String, String> locales = new HashMap<>();
            for (int column = 1; column < table.getColumnCount(); column++) {
                locales.put(table.get(0, column), table.get(row, column));
            }
            lookup.put(internal, Collections.unmodifiableMap(locales));
        }
        this.localizedLookup = Collections.unmodifiableMap(lookup);
    }

    public Set<String> getInternalNames() {
        return internalNames;
    }

    public String getLocalizedName(String internalName, String locale) {
        return localizedLookup.get(internalName).get(locale);
    }
}
