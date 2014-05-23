package io.github.appstash.task;

import io.github.appstash.model.AppStashClassLoading;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

/**
 * @author zutherb
 */
public class ClassLoadingLoggingTask extends AbstractLoggingTask implements Runnable {

    private static final String CLASS_LOADING = "ClassLoading";

    @Override
    public void run() {
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        try {
            getRestTemplate().put("http://10.211.55.100:9200/analytics/classes/{id}",
                    AppStashClassLoading.builder()
                            .name(classLoadingMXBean.getObjectName().getCanonicalName())
                            .type(CLASS_LOADING)
                            .host(InetAddress.getLocalHost().getHostName())
                            .ip(InetAddress.getLocalHost().getHostAddress())
                            .timestamp(new Date())
                            .loadedClassCount(classLoadingMXBean.getLoadedClassCount())
                            .totalLoadedClassCount(classLoadingMXBean.getTotalLoadedClassCount())
                            .unloadedClassCount(classLoadingMXBean.getUnloadedClassCount())
                            .create(),
                    UUID.randomUUID().toString()
            );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
