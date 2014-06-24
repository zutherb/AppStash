package io.github.appstash.memoryleak;

import io.github.appstash.memoryleak.classloader.ClassLoaderLoggingAgentLauncher;
import io.github.appstash.memoryleak.command.Command;
import io.github.appstash.memoryleak.command.CommandChainFactory;
import io.github.appstash.memoryleak.command.SimulationChoiceWriter;
import io.github.appstash.memoryleak.simulation.AbstractMemoryLeakSimulator;
import io.github.appstash.memoryleak.simulation.SimulatorType;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zutherb
 *         -Xbootclasspath/p:/Users/berndzuther/appstash/memory-leak/build/libs/; -verbose:class -Djava.system.class.loader=io.github.appstash.memoryleak.classloader.LoggingClassLoader
 */
public class Runner {

    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(1);
    private static final List<Command> PRINTER_CHAIN = CommandChainFactory.createPrinterChain();

    public static void main(String[] args) throws Exception {
        ClassLoaderLoggingAgentLauncher.launch(args);
        SimulationChoiceWriter.getPrinter().execute();
        Scanner scanner = new Scanner(System.in);
        SimulatorType simulatorType = SimulatorType.values()[(args.length > 0) ? Integer.valueOf(args[0]) : scanner.nextInt()];
        AbstractMemoryLeakSimulator memoryLeakSimulator = simulatorType.getMemoryLeakSimulator();
        THREAD_POOL.submit(memoryLeakSimulator);
        while (memoryLeakSimulator.isNotCrashed()) {
            for (Command command : PRINTER_CHAIN) {
                command.execute();
            }
        }
    }
}
