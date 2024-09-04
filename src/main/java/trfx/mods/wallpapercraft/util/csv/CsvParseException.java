package trfx.mods.wallpapercraft.util.csv;

public class CsvParseException extends RuntimeException {
    public CsvParseException() {
        super();
    }

    public CsvParseException(String message) {
        super(message);
    }

    public CsvParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvParseException(Throwable cause) {
        super(cause);
    }
}
