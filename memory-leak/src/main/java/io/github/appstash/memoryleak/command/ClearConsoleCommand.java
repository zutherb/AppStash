package io.github.appstash.memoryleak.command;

/**
 * @author zutherb
 */
public class ClearConsoleCommand extends AbstractPrinterContext implements Command {
    private static final Command INSTANCE = new ClearConsoleCommand();

    @Override
    public void execute() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ConsolePrinter.writeLine("\033[2J\033[1;1H");
    }

    public static Command getInstance() {
        return INSTANCE;
    }

    @Override
    protected int getLength() {
        return 0;
    }
}
