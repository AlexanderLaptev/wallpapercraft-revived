package trfx.mods.wallpapercraft.core.csv;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CsvTable {
    /**
     * A column-major table of {@link String} entries.
     */
    private final List<List<String>> table = new ArrayList<>();

    public CsvTable(String csv, char delimiter) {
        String[] lines = csv.split("\\r?\\n");
        int lineNumber = 1;
        for (String line : lines) {
            parseRow(line, lineNumber, delimiter);
        }
    }

    private void parseRow(String csvLine, int lineNumber, char delimiter) {
        try {
            table.add(StringSplitter.splitCsvString(csvLine, delimiter));
        } catch (CsvParseException e) {
            throw new CsvParseException("Exception while parsing line " + lineNumber, e);
        }
    }

    public String get(int row, int column) {
        return table.get(column).get(row);
    }

    public int getRowCount(int column) {
        return table.get(column).size();
    }

    public int getColumnCount() {
        return table.size();
    }

    public Iterable<String> getRowIterable(int row) {
        return new Iterable<String>() {
            @Override
            public @NotNull Iterator<String> iterator() {
                return new Iterator<String>() {
                    int column = 0;

                    @Override
                    public boolean hasNext() {
                        return column < getColumnCount();
                    }

                    @Override
                    public String next() {
                        return get(row, column++);
                    }
                };
            }
        };
    }

    public Iterable<String> getColumnIterable(int column) {
        return new Iterable<String>() {
            @Override
            public @NotNull Iterator<String> iterator() {
                return new Iterator<String>() {
                    int row = 0;

                    @Override
                    public boolean hasNext() {
                        return row < getRowCount(column);
                    }

                    @Override
                    public String next() {
                        return get(row++, column);
                    }
                };
            }
        };
    }

    public List<String> getColumnAsList(int column) {
        return Collections.unmodifiableList(table.get(column));
    }

    public List<String> getRowAsList(int row) {
        List<String> result = new ArrayList<>();
        for (String value : getRowIterable(row)) {
            result.add(value);
        }
        return Collections.unmodifiableList(result);
    }
}
