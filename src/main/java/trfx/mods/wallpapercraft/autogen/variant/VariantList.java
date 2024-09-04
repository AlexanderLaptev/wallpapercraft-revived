package trfx.mods.wallpapercraft.autogen.variant;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import trfx.mods.wallpapercraft.util.csv.CsvTable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VariantList {
    public static class Variant {
        private static final String VALID_NAME_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789_";
        private final String internalName;

        private final boolean usesRed;

        private final boolean usesGreen;

        private final boolean usesBlue;

        public Variant(String internalName, String colors) {
            this.internalName = validateName(internalName);
            Validate.isTrue(StringUtils.containsOnly(colors, "rgb"), "Invalid colors: '%s'", colors);
            usesRed = colors.contains("r");
            usesGreen = colors.contains("g");
            usesBlue = colors.contains("b");
        }

        private static String validateName(String internalName) {
            Validate.notEmpty(internalName, "Internal name '%s' is empty", internalName);
            if (!StringUtils.containsOnly(internalName, VALID_NAME_CHARS)) {
                throw new IllegalArgumentException(String.format(
                        "Internal name '%s' contains invalid characters",
                        internalName
                ));
            }
            return internalName;
        }

        public String getInternalName() {
            return internalName;
        }

        public boolean getUsesRed() {
            return usesRed;
        }

        public boolean getUsesGreen() {
            return usesGreen;
        }

        public boolean getUsesBlue() {
            return usesBlue;
        }
    }

    private final Map<String, Variant> variants;

    public VariantList(CsvTable csvTable) {
        validateTable(csvTable);

        var variants = new HashMap<String, Variant>();
        for (int row = 1; row < csvTable.getRowCount(); row++) {
            parseTableRow(csvTable, row, variants);
        }

        this.variants = Collections.unmodifiableMap(variants);
    }

    private static void validateTable(CsvTable csvTable) {
        Validate.isTrue(csvTable.getRowCount() == 2, "Variant table must only contain two rows");
        Validate.isTrue(csvTable.getColumnCount() >= 2, "At least one variant must be specified in variant table");
        Validate.isTrue("name".equals(csvTable.get(0, 0)), "Column `name` not found in variant table");
        Validate.isTrue("paint".equals(csvTable.get(0, 1)), "Column `paint` not found in variant table");
    }

    private static void parseTableRow(CsvTable csvTable, int row, Map<String, Variant> variants) {
        String internalName = csvTable.get(row, 0);
        String colors = csvTable.get(row, 1);
        Validate.isTrue(
                variants.put(internalName, new Variant(internalName, colors)) == null,
                "Duplicate variant found in row %d",
                row + 1
        );
    }

    public Map<String, Variant> getVariants() {
        return variants;
    }
}
