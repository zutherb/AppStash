package io.github.appstash.memoryleak.simulator;

/**
 * @author zutherb
 */
public enum Simulator {
    STRING_INTERN(new StringInterningMemoryLeakSimulator()),
    GROOVY_CLASSLOADING(new GroovyClassloadingMemoryLeakSimulator());

    private AbstractMemoryLeakSimulator memoryLeakSimulator;

    Simulator(AbstractMemoryLeakSimulator memoryLeakSimulator) {
        this.memoryLeakSimulator = memoryLeakSimulator;
    }


    public AbstractMemoryLeakSimulator getMemoryLeakSimulator() {
        return memoryLeakSimulator;
    }
}
