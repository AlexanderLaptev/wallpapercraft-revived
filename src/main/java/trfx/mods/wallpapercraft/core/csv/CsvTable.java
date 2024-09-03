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

    public CsvTable(String csv) {
        this(csv, ',');
    }

    public CsvTable(Iterable<String> csvLines) {
        this(csvLines, ',');
    }

    public CsvTable(String csv, char delimiter) {
        this(csv.lines().toList(), delimiter);
    }

    public CsvTable(Iterable<String> csvLines, char delimiter) {
        int lineIndex = 0;
        for (String line : csvLines) {
            parseRow(line, lineIndex++, delimiter);
        }
    }

    private void parseRow(String csvLine, int lineIndex, char delimiter) {
        try {
            table.add(StringSplitter.splitCsvString(csvLine, delimiter));
        } catch (CsvParseException e) {
            throw new CsvParseException("Exception while parsing line " + (lineIndex + 1), e);
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
