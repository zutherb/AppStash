package io.github.appstash.memoryleak.printer;

/**
 * @author zutherb
 */
public class MemoryProgressPrinter implements ConsolePrinter {

    private MemoryProgressPrinterContext printerContext;

    public MemoryProgressPrinter(MemoryProgressPrinterContext printerContext) {
        this.printerContext = printerContext;
    }

    @Override
    public void print() {
        final int width = 50; // progress bar width in chars

        System.out.print(printerContext.getName() + ": [");
        int i = 0;
        for (; i <= (int) (getPercentage() * width); i++) {
            System.out.print(".");
        }
        for (; i < width; i++) {
            System.out.print(" ");
        }
        System.out.print("]\n");
    }

    private double getPercentage() {
        double used = printerContext.getUsed();
        double max = printerContext.getMax();
        return used / max;
    }
}
