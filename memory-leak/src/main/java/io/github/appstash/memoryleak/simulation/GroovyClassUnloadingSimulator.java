package io.github.appstash.memoryleak.simulation;

import groovy.lang.GroovyShell;

/**
 * @author zutherb
 */
public class GroovyClassUnloadingSimulator extends AbstractMemoryLeakSimulator {

    @Override
    protected void doLeakSimulation() {
        GroovyShell groovyShell = new GroovyShell(getClass().getClassLoader());
        groovyShell.evaluate("1 + 1");
    }
}
