package io.github.appstash.memoryleak.command;

import io.github.appstash.memoryleak.logging.ConsoleLogger;

/**
 * @author zutherb
 */
public class MemoryProgressPrintCommand implements Command {
    private static final ConsoleLogger LOGGER = ConsoleLogger.getConsoleLogger();

    private MemoryProgressPrintCommandContext printerContext;

    public MemoryProgressPrintCommand(MemoryProgressPrintCommandContext printerContext) {
        this.printerContext = printerContext;
    }

    @Override
    public void execute() {
        final int width = 50; // progress bar width in chars

        StringBuilder builder = new StringBuilder();
        builder.append(printerContext.getName() + ": [");
        int i = 0;
        for (; i <= (int) (getPercentage() * width); i++) {
            builder.append(".");
        }
        for (; i < width; i++) {
            builder.append(" ");
        }
        builder.append("]");
        LOGGER.log(builder.toString());
    }

    private double getPercentage() {
        double used = printerContext.getUsed();
        double max = printerContext.getMax();
        return used / max;
    }

}
