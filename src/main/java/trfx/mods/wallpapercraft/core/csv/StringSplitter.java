package trfx.mods.wallpapercraft.core.csv;

import java.util.ArrayList;
import java.util.List;

public class StringSplitter {
    public static List<String> splitCsvString(String csvString) {
        return splitCsvString(csvString, ',');
    }

    public static List<String> splitCsvString(String csvString, char delimiter) {
        List<String> result = new ArrayList<>();

        char[] chars = csvString.toCharArray();
        int position = 0;
        int lastPosition = chars.length - 1;

        StringBuilder valueBuilder = new StringBuilder();
        boolean inQuotes = false;
        while (position <= lastPosition) {
            char c = chars[position];
            if (c == delimiter) {
                if (!inQuotes) {
                    result.add(valueBuilder.toString().trim());
                    valueBuilder.setLength(0);
                } else {
                    valueBuilder.append(c);
                }
            } else if (c == '"') {
                if (position == 0 || chars[position - 1] == delimiter) {
                    inQuotes = true;
                } else if (position == lastPosition || chars[position + 1] == delimiter) {
                    inQuotes = false;
                } else if (chars[position + 1] == '"') {
                    if (!inQuotes) {
                        throw new CsvParseException(String.format(
                                "Encountered an escaped quote in an unquoted value at position %d in string '%s'",
                                position,
                                csvString
                        ));
                    }
                    valueBuilder.append('"');
                    position++;
                } else {
                    throw new CsvParseException(String.format(
                            "Unexpected quote at position %d in string '%s'",
                            position,
                            csvString
                    ));
                }
            } else {
                valueBuilder.append(c);
            }
            position++;
        }

        result.add(valueBuilder.toString().trim());
        return result;
    }
}
