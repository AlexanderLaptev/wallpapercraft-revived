package trfx.mods.wallpapercraft.autogen.variant;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.*;

public class VariantsDefinition {
    private static final String VALID_NAME_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789_";

    public static class Group {
        private final List<String> variants;

        private final boolean usesRed;
        private final boolean usesGreen;
        private final boolean usesBlue;

        public Group(String colors, List<String> variants) {
            this.variants = Collections.unmodifiableList(variants);

            Validate.isTrue(StringUtils.containsOnly(colors, "rgb"));
            this.usesRed = colors.contains("r");
            this.usesGreen = colors.contains("g");
            this.usesBlue = colors.contains("b");
        }

        public List<String> getVariants() { return variants; }

        public boolean getUsesRed() { return usesRed; }

        public boolean getUsesGreen() { return usesGreen; }

        public boolean getUsesBlue() { return usesBlue; }
    }

    private final String id;

    private final Map<String, Group> variantsToGroups;

    private final List<Group> groups;

    public VariantsDefinition(String id, Map<String, Group> variantsToGroups, List<Group> groups) {
        Validate.isTrue(
                StringUtils.containsOnly(id, VALID_NAME_CHARS),
                "Variant definition name contains illegal characters: %s",
                id
        );
        this.id = id;
        this.variantsToGroups = Collections.unmodifiableMap(variantsToGroups);
        this.groups = Collections.unmodifiableList(groups);
    }

    public static VariantsDefinition fromJson(String id, JsonArray array) {
        Map<String, Group> variantsToGroups = new LinkedHashMap<>();
        List<Group> groups = new ArrayList<>();

        for (JsonElement topLevelElem : array) {
            JsonObject asObject = topLevelElem.getAsJsonObject();
            String colors = asObject.get("colors").getAsString();
            List<String> groupVariants = new ArrayList<>();

            Group group = new Group(colors, groupVariants);
            groups.add(group);

            JsonArray variantArray = asObject.get("variants").getAsJsonArray();
            for (JsonElement variantElem : variantArray) {
                String variantName = variantElem.getAsString();
                Validate.isTrue(
                        variantsToGroups.put(variantName, group) == null,
                        "Duplicate variant '%s' found in variants definition '%s'",
                        variantName,
                        id
                );
                groupVariants.add(variantName);
            }
        }

        return new VariantsDefinition(id, variantsToGroups, groups);
    }

    public String getId() { return id; }

    public Set<String> getVariants() { return variantsToGroups.keySet(); }

    public Collection<Group> getGroups() { return groups; }

    public Group getGroupForVariant(String variant) { return variantsToGroups.get(variant); }
}
