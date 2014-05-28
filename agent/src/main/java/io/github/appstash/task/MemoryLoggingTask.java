package io.github.appstash.task;

import io.github.appstash.model.MemoryUsageInfo;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;


/**
 * @author zutherb
 */
public class MemoryLoggingTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryLoggingTask.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void run() {
        for (MemoryPoolMXBean memoryPoolMXBean : ManagementFactory.getMemoryPoolMXBeans()) {
            try {
                LOGGER.info(OBJECT_MAPPER.writeValueAsString(MemoryUsageInfo.builder()
                        .name(memoryPoolMXBean.getName())
                        .type(memoryPoolMXBean.getType().name())
                        .usage(memoryPoolMXBean.getUsage())
                        .build()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
