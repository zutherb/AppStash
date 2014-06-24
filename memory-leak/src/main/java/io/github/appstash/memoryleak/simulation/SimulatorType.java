package io.github.appstash.memoryleak.simulation;

/**
 * @author zutherb
 */
public enum SimulatorType {
    STRING_INTERN(new StringInterningMemoryLeakSimulator(), "String Interning Leak: Simulate PermGen OutOfError"),
    GROOVY_CLASSLOADING(new GroovyClassloadingMemoryLeakSimulator(), "ClassLoader Leak: Simulate PermGen OutOfError"),
    CLASSLOADING(new ClassLoaderLeakSimulator(), "ClassLoader Leak: Simulate PermGen OutOfError"),
    ARRAY(new ArrayMemoryLeakSimulator(), ""),
    ARRAY_INTERN(new ArrayInternSimulator(), ""),
    CACHE(new CacheMemoryLeakSimulator(), "");

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
