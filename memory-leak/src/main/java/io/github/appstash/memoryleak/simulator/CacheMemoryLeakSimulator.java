package io.github.appstash.memoryleak.simulator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zutherb
 */
public class CacheMemoryLeakSimulator extends AbstractMemoryLeakSimulator {

    private static Map<String, String> cache = new HashMap<String, String>();

    @Override
    protected void doLeakSimulation() {
        cache.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());

    }
}
