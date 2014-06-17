package io.github.appstash.memoryleak.simulator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zutherb
 */
public class ArrayInternSimulator extends AbstractMemoryLeakSimulator {

    private int counter = 0;
    private String[] strings = {"1", "2", "3", "4", "5"};
    private List<String> leak = new ArrayList<String>();

    @Override
    protected void doLeakSimulation() {
        leak.add(strings[getCounter()].intern());
    }

    private int getCounter() {
        if (counter >= strings.length) counter = 0;
        return counter++;
    }
}
