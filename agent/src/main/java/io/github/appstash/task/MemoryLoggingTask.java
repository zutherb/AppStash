package io.github.appstash.task;

import io.github.appstash.model.AppStashMemoryUsage;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

/**
 * @author zutherb
 */
public class MemoryLoggingTask extends AbstractLoggingTask implements Runnable {


    @Override
    public void run() {
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        for (MemoryPoolMXBean memoryPoolMXBean : ManagementFactory.getMemoryPoolMXBeans()) {
            try {
                getRestTemplate().put("http://10.211.55.100:9200/analytics/memory/{id}",
                        AppStashMemoryUsage.builder()
                                .usage(memoryPoolMXBean.getUsage())
                                .name(memoryPoolMXBean.getName())
                                .type(memoryPoolMXBean.getType().toString())
                                .host(InetAddress.getLocalHost().getHostName())
                                .ip(InetAddress.getLocalHost().getHostAddress())
                                .timestamp(new Date())
                                .create(),
                        UUID.randomUUID().toString()
                );
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

    }
}
