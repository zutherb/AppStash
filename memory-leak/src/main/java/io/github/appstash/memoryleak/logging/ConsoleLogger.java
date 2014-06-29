package io.github.appstash.memoryleak.logging;

import java.util.logging.*;

/**
 * @author zutherb
 */
public class ConsoleLogger {
    private static final Logger LOGGER = Logger.getLogger(SpaceCalculator.class.getName());
    private static final ConsoleLogger CONSOLE_LOGGER = new ConsoleLogger();

    private ConsoleLogger () { /* NOOP */ }

    static {
        LOGGER.setUseParentHandlers(false);
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
            public String format(LogRecord record) {
                return record.getMessage() + "\n";
            }
        });
        LOGGER.addHandler(consoleHandler);

    }

    public void log(String s) {
        LOGGER.log(Level.INFO, s);
    }

    public static ConsoleLogger getConsoleLogger(){
        return CONSOLE_LOGGER;
    }
}
