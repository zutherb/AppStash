package io.github.appstash.memoryleak.command;

import io.github.appstash.memoryleak.simulation.SimulatorType;

/**
 * @author zutherb
 */
public class SimulationChoiceCommand implements Command {

    private static final SimulationChoiceCommand INSTANCE = new SimulationChoiceCommand();

    private SimulationChoiceCommand() {/*NOOP*/}

    @Override
    public void execute() {
        for (SimulatorType simulatorType : SimulatorType.values()) {
            System.out.println(simulatorType.ordinal() + ". " + simulatorType.getDescription());
        }
        System.out.print("Choice simulation: ");
    }

    public static SimulationChoiceCommand getPrinter() {
        return INSTANCE;
    }
}
