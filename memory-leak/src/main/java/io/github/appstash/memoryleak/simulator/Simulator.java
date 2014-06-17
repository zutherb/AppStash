package io.github.appstash.memoryleak.simulator;

/**
 * @author zutherb
 */
public enum Simulator {
    STRING_INTERN(new StringInterningMemoryLeakSimulator()),
    GROOVY_CLASSLOADING(new GroovyClassloadingMemoryLeakSimulator()),
    ARRAY(new ArrayMemoryLeakSimulator()),
    ARRAY_INTERN(new ArrayInternSimulator()),
    CACHE(new CacheMemoryLeakSimulator());

    private AbstractMemoryLeakSimulator memoryLeakSimulator;

    Simulator(AbstractMemoryLeakSimulator memoryLeakSimulator) {
        this.memoryLeakSimulator = memoryLeakSimulator;
    }


    public AbstractMemoryLeakSimulator getMemoryLeakSimulator() {
        return memoryLeakSimulator;
    }
}
