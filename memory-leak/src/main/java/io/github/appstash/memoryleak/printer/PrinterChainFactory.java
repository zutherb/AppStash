package io.github.appstash.memoryleak.printer;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zutherb
 */
public class PrinterChainFactory {
    public static List<ConsolePrinter> createPrinterChain() {
        ArrayList<ConsolePrinter> printerChain = new ArrayList<>();
        printerChain.add(new ProcessIdPrinter());
        for (MemoryPoolMXBean mxBean : ManagementFactory.getMemoryPoolMXBeans()) {
            printerChain.add(new MemoryProgressPrinter(new MemoryProgressPrinterContext(mxBean)));
        }
        printerChain.add(ClearConsolePrinter.getPrinter());
        return Collections.unmodifiableList(printerChain);
    }
}
