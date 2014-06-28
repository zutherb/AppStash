package io.github.appstash.memoryleak.simulation;

/**
 * @author zutherb
 */
public enum SimulatorType {
    STRING_INTERN(new StringInterningMemoryLeakSimulator(), "Interning Leak: Simulate OutOfError: PermGen"),
    GROOVY_CLASSLOADING(new GroovyClassloadingMemoryLeakSimulator(), "ClassLoader Leak: Simulate OutOfError: PermGen"),
    CACHE(new CacheMemoryLeakSimulator(), "Heap Leak: Simulate OutOfError: Heap Space");

    private AbstractMemoryLeakSimulator memoryLeakSimulator;
    private String description;

    SimulatorType(AbstractMemoryLeakSimulator memoryLeakSimulator, String description) {
        this.memoryLeakSimulator = memoryLeakSimulator;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public AbstractMemoryLeakSimulator getMemoryLeakSimulator() {
        return memoryLeakSimulator;
    }
}
