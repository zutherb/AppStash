package io.github.appstash.task;

import io.github.appstash.model.MemoryUsageInfo;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;


/**
 * @author zutherb
 */
public class MemoryLoggingTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryLoggingTask.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final List<MemoryPoolMXBean> MEMORY_POOL_MX_BEANS = ManagementFactory.getMemoryPoolMXBeans();

    @Override
    public void run() {
        for (MemoryPoolMXBean memoryPoolMXBean : MEMORY_POOL_MX_BEANS) {
            try {
                String message = memoryUsageAsJsonString(memoryPoolMXBean);
                LOGGER.info(message);
            } catch (IOException e) {
                LOGGER.error("Object could not converted to JSON String:", e);
            }
        }

    }

    private String memoryUsageAsJsonString(MemoryPoolMXBean memoryPoolMXBean) throws IOException {
        return OBJECT_MAPPER.writeValueAsString(buildMemoryUsageInfo(memoryPoolMXBean));
    }

    private MemoryUsageInfo buildMemoryUsageInfo(MemoryPoolMXBean memoryPoolMXBean) {
        return MemoryUsageInfo.builder()
                .name(memoryPoolMXBean.getName())
                .type(memoryPoolMXBean.getType().toString())
                .usage(memoryPoolMXBean.getUsage())
                .build();
    }
}
