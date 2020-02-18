package tutorials.logging;

import java.util.function.Supplier;

public interface Logger {

    void log(String message);

    void log(String format, Object... args);

    void log(Supplier<String> message);
}
