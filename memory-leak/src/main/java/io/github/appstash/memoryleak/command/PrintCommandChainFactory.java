package io.github.appstash.memoryleak.command;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zutherb
 */
public class PrintCommandChainFactory {
    public static List<Command> createPrinterChain() {
        ArrayList<Command> printerChain = new ArrayList<Command>();
        printerChain.add(new ProcessIdWriter());
        for (MemoryPoolMXBean mxBean : ManagementFactory.getMemoryPoolMXBeans()) {
            printerChain.add(new MemoryProgressPrintCommand(new MemoryProgressPrintCommandContext(mxBean)));
        }
        printerChain.add(ClassesPrintCommand.getInstance());
        printerChain.add(ClearConsolePrintCommand.getInstance());
        return Collections.unmodifiableList(printerChain);
    }
}
