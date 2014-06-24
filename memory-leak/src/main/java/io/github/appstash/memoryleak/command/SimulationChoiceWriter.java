package io.github.appstash.memoryleak.command;

import io.github.appstash.memoryleak.simulation.SimulatorType;

/**
 * @author zutherb
 */
public class SimulationChoiceWriter implements Command {

    private static final SimulationChoiceWriter INSTANCE = new SimulationChoiceWriter();

    private SimulationChoiceWriter() {/*NOOP*/}

    @Override
    public void execute() {
        for (SimulatorType simulatorType : SimulatorType.values()) {
            System.out.println(simulatorType.ordinal() + ". " + simulatorType.name());
        }
        System.out.print("Choice simulation: ");
    }

    public static SimulationChoiceWriter getPrinter() {
        return INSTANCE;
    }
}
