package io.github.appstash.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.appstash.model.MemoryUsageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;


/**
 * @author zutherb
 */
public class CpuLoggingTask extends AbstractLoggingTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CpuLoggingTask.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final OperatingSystemMXBean RUNTIME_MX_BEAN = ManagementFactory.getOperatingSystemMXBean();

    @Override
    public void run() {
        RUNTIME_MX_BEAN.getArch();
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
