package trfx.mods.wallpapercraft.core.csv;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CsvTable {
    /**
     * A column-major table of {@link java.lang.String} entries.
     */
    private final List<List<String>> table = new ArrayList<>();

    private final int rowCount;

    private final int columnCount;

    public CsvTable(String csv) {
        this(csv.lines().toList());
    }

    public CsvTable(Iterable<String> csvLines) {
        int lineIndex = 0;
        for (String line : csvLines) {
            parseRow(line, lineIndex++);
        }
        columnCount = table.size();
        rowCount = table.stream().mapToInt(List::size).max().getAsInt();
    }

    private void parseRow(String csvLine, int lineIndex) {
        try {
            table.add(StringSplitter.splitCsvString(csvLine));
        } catch (CsvParseException e) {
            throw new CsvParseException("Exception while parsing line " + (lineIndex + 1), e);
        }
    }

    public String get(int row, int column) {
        return table.get(column).get(row);
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
                        return column < columnCount;
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
                        return row < rowCount;
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
