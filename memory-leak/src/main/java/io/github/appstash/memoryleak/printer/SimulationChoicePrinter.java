package io.github.appstash.memoryleak.printer;

import io.github.appstash.memoryleak.simulator.Simulator;

/**
 * @author zutherb
 */
public class SimulationChoicePrinter implements ConsolePrinter {

    private static final SimulationChoicePrinter INSTANCE = new SimulationChoicePrinter();

    private SimulationChoicePrinter() {/*NOOP*/}

    @Override
    public void print() {
        for (Simulator simulator : Simulator.values()) {
            System.out.println(simulator.ordinal() + ". " + simulator.name());
        }
        System.out.print("Choice simulation: ");
    }

    public static SimulationChoicePrinter getPrinter() {
        return INSTANCE;
    }
}
