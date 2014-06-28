package io.github.appstash.memoryleak;

import io.github.appstash.memoryleak.classloader.ClassLoaderLoggingAgentLauncher;
import io.github.appstash.memoryleak.command.Command;
import io.github.appstash.memoryleak.command.PrintCommandChainFactory;
import io.github.appstash.memoryleak.command.SimulationChoiceCommand;
import io.github.appstash.memoryleak.simulation.AbstractMemoryLeakSimulator;
import io.github.appstash.memoryleak.simulation.SimulatorType;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zutherb
 */
public class Runner {

    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(1);
    private static final List<Command> PRINTER_CHAIN = PrintCommandChainFactory.createPrinterChain();

    public static void main(String[] args) {
        ClassLoaderLoggingAgentLauncher.launch(args);
        SimulatorType simulatorType = showSimulationSelection();
        AbstractMemoryLeakSimulator memoryLeakSimulator = submitSimulation(simulatorType);
        printDebugInformation(memoryLeakSimulator);
    }

    private static void printDebugInformation(AbstractMemoryLeakSimulator memoryLeakSimulator) {
        while (memoryLeakSimulator.isNotCrashed()) {
            for (Command command : PRINTER_CHAIN) {
                command.execute();
            }
        }
    }

    private static AbstractMemoryLeakSimulator submitSimulation(SimulatorType simulatorType) {
        AbstractMemoryLeakSimulator memoryLeakSimulator = simulatorType.getMemoryLeakSimulator();
        THREAD_POOL.submit(memoryLeakSimulator);
        return memoryLeakSimulator;
    }

    private static SimulatorType showSimulationSelection() {
        SimulatorType simulatorType = null;
        while (simulatorType == null) {
            try {
                Scanner scanner = new Scanner(System.in);
                SimulationChoiceCommand.getPrinter().execute();
                simulatorType = SimulatorType.values()[scanner.nextInt()];
            } catch (Exception e) {
            }
        }
        return simulatorType;
    }
}
