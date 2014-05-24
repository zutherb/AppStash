package io.github.appstash.task;

import io.github.appstash.model.AppStashClassLoading;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Date;

/**
 * @author zutherb
 */
public class CpuLoggingTask extends AbstractLoggingTask implements Runnable {

    @Override
    public void run() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        AppStashClassLoading appStashClassLoading = AppStashClassLoading.builder()
                .host(getHostName())
                .ip(getHostAddress())
                .timestamp(new Date())
                .create();


    }


    @Override
    protected String getTypeName() {
        return "cpu";
    }
}
