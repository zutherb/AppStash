package io.github.appstash.memoryleak.command;

import io.github.appstash.memoryleak.logging.ConsoleLogger;

/**
 * @author zutherb
 */
public class ClearConsolePrintCommand implements Command {
    private static final Command INSTANCE = new ClearConsolePrintCommand();
    private static final ConsoleLogger LOGGER = ConsoleLogger.getConsoleLogger();

    @Override
    public void execute() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.log("\033[2J\033[1;1H");
    }

    public static Command getInstance() {
        return INSTANCE;
    }
}
