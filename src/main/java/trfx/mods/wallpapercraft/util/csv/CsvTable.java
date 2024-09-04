package trfx.mods.wallpapercraft.util.csv;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CsvTable {
    /**
     * A row-major table of {@link String} entries.
     */
    private final List<List<String>> table = new ArrayList<>();

    private final int rowCount;

    private final int columnCount;

    public CsvTable(String csv) {
        this(csv, ',');
    }

    public CsvTable(String csv, char delimiter) {
        if (csv.isBlank()) {
            throw new CsvParseException("Blank CSV string, nothing to parse");
        }
        String[] lines = csv.split("\\r?\\n");
        int lineIndex = 0;
        for (String line : lines) {
            parseRow(line, lineIndex + 1, delimiter);
            lineIndex++;
        }
        rowCount = lineIndex;
        columnCount = table.get(0).size();
    }

    private void parseRow(String csvLine, int lineNumber, char delimiter) {
        try {
            List<String> split = StringSplitter.splitCsvLine(csvLine, delimiter);
            if (!table.isEmpty() && table.get(0).size() != split.size()) {
                throw new CsvParseException("All CSV rows must have the same length");
            }
            table.add(StringSplitter.splitCsvLine(csvLine, delimiter));
        } catch (CsvParseException e) {
            throw new CsvParseException("Exception while parsing line " + lineNumber, e);
        }
    }

    public String get(int row, int column) {
        return table.get(row).get(column);
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Iterable<String> getRowIterable(int row) {
        return new Iterable<>() {
            @Override
            public @NotNull Iterator<String> iterator() {
                return new Iterator<>() {
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
        return new Iterable<>() {
            @Override
            public @NotNull Iterator<String> iterator() {
                return new Iterator<>() {
                    int row = 0;

                    @Override
                    public boolean hasNext() {
                        return row < getRowCount();
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
