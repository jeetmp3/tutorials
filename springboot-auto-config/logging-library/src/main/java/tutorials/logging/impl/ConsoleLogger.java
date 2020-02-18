package tutorials.logging.impl;

import tutorials.logging.Logger;

import java.util.function.Supplier;

public class ConsoleLogger implements Logger {

    @Override
    public void log(String message) {
        System.out.println(message);
    }

    @Override
    public void log(String format, Object... args) {
        System.out.printf(format, args);
    }

    @Override
    public void log(Supplier<String> message) {
        System.out.println(message.get());
    }
}
