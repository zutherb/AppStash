package io.github.appstash.memoryleak.command;

import java.util.logging.*;

/**
 * @author zutherb
 */
public class ConsolePrinter {
    private static final Logger LOGGER = Logger.getLogger(AbstractPrinterContext.class.getName());

    static {
        LOGGER.setUseParentHandlers(false);
        Handler conHdlr = new ConsoleHandler();
        conHdlr.setFormatter(new Formatter() {
            public String format(LogRecord record) {
                return record.getMessage() + "\n";
            }
        });
        LOGGER.addHandler(conHdlr);

    }

    public static void writeLine(String s) {
        LOGGER.log(Level.INFO, s);
    }
}
