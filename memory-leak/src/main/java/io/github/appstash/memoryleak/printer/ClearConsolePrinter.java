package io.github.appstash.memoryleak.printer;

/**
 * @author zutherb
 */
public class ClearConsolePrinter implements ConsolePrinter {
    private static final ConsolePrinter INSTANCE = new ClearConsolePrinter();

    @Override
    public void print() {
        try {
            Thread.sleep(500);
            System.out.println("\u001b[2J");
            System.out.flush();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConsolePrinter getPrinter() {
        return INSTANCE;
    }
}
