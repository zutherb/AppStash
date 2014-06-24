package io.github.appstash.memoryleak.command;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zutherb
 */
public class CommandChainFactory {
    public static List<Command> createPrinterChain() {
        ArrayList<Command> printerChain = new ArrayList<Command>();
        printerChain.add(new ProcessIdWriter());
        for (MemoryPoolMXBean mxBean : ManagementFactory.getMemoryPoolMXBeans()) {
            printerChain.add(new MemoryProgressWriter(new MemoryProgressPrinterContext(mxBean)));
        }

        printerChain.add(ClearConsoleCommand.getInstance());
        return Collections.unmodifiableList(printerChain);
    }
}
