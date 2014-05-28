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
                String msg = OBJECT_MAPPER.writeValueAsString(buildMemoryUsageInfo(memoryPoolMXBean));
                LOGGER.info(msg);
            } catch (IOException e) {
                LOGGER.error("Object could not be converted to JSON String:", e);
            }
        }

    }

    private MemoryUsageInfo buildMemoryUsageInfo(MemoryPoolMXBean memoryPoolMXBean) {
        return MemoryUsageInfo.builder()
                .name(memoryPoolMXBean.getName())
                .type(memoryPoolMXBean.getType().toString())
                .usage(memoryPoolMXBean.getUsage())
                .build();
    }
}
