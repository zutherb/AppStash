package io.github.appstash.memoryleak.printer;

import java.lang.management.ManagementFactory;

/**
 * @author zutherb
 */
public class ProcessIdPrinter implements ConsolePrinter {
    @Override
    public void print() {
        System.out.println("ProcessId: " + ManagementFactory.getRuntimeMXBean().getName());
    }
}
