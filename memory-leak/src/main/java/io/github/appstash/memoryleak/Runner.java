package io.github.appstash.memoryleak;

import io.github.appstash.memoryleak.printer.ConsolePrinter;
import io.github.appstash.memoryleak.printer.PrinterChainFactory;
import io.github.appstash.memoryleak.printer.SimulationChoicePrinter;
import io.github.appstash.memoryleak.simulator.AbstractMemoryLeakSimulator;
import io.github.appstash.memoryleak.simulator.Simulator;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zutherb
 */
public class Runner {

    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(1);
    private static final List<ConsolePrinter> PRINTER_CHAIN = PrinterChainFactory.createPrinterChain();

    public static void main(String[] args) throws InterruptedException, IOException {
        SimulationChoicePrinter.getPrinter().print();
        Scanner scanner = new Scanner(System.in);
        Simulator simulator = null;
        while (simulator == null) {
            try {
                simulator = Simulator.values()[scanner.nextInt()];
            } catch (Exception e) {
                SimulationChoicePrinter.getPrinter().print();
            }
        }
        AbstractMemoryLeakSimulator memoryLeakSimulator = simulator.getMemoryLeakSimulator();
        THREAD_POOL.submit(memoryLeakSimulator);
        while (memoryLeakSimulator.isNotCrashed()) {
            for (ConsolePrinter consolePrinter : PRINTER_CHAIN) {
                consolePrinter.print();
            }
        }
    }
}
