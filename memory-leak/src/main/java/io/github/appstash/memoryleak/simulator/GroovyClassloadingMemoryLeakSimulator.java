package io.github.appstash.memoryleak.simulator;

import groovy.lang.GroovyShell;

/**
 * @author zutherb
 */
public class GroovyClassloadingMemoryLeakSimulator extends AbstractMemoryLeakSimulator {
    private static final GroovyShell GROOVY_SHELL = new GroovyShell(GroovyClassloadingMemoryLeakSimulator.class.getClassLoader());

    @Override
    protected void doLeakSimulation() {
        GROOVY_SHELL.evaluate("1 + 1");
    }
}
