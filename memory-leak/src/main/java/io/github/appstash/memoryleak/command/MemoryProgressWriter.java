package io.github.appstash.memoryleak.command;

/**
 * @author zutherb
 */
public class MemoryProgressWriter implements Command {

    private MemoryProgressPrinterContext printerContext;

    public MemoryProgressWriter(MemoryProgressPrinterContext printerContext) {
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
        ConsolePrinter.writeLine(builder.toString());
    }

    private double getPercentage() {
        double used = printerContext.getUsed();
        double max = printerContext.getMax();
        return used / max;
    }

}
