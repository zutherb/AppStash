package io.github.appstash.task;

import io.github.appstash.model.AppStashMemoryUsage;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.Date;

/**
 * @author zutherb
 */
public class MemoryLoggingTask extends AbstractLoggingTask<AppStashMemoryUsage> implements Runnable {


    @Override
    public void run() {
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        for (MemoryPoolMXBean memoryPoolMXBean : ManagementFactory.getMemoryPoolMXBeans()) {

            AppStashMemoryUsage appStashMemoryUsage = AppStashMemoryUsage.builder()
                    .usage(memoryPoolMXBean.getUsage())
                    .name(memoryPoolMXBean.getName())
                    .type(memoryPoolMXBean.getType().toString())
                    .host(getHostName())
                    .ip(getHostAddress())
                    .timestamp(new Date())
                    .create();
            log(appStashMemoryUsage);

        }

    }

    @Override
    protected String getTypeName() {
        return "memory";
    }
}
