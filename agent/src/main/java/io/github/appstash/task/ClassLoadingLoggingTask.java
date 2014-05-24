package io.github.appstash.task;

import io.github.appstash.model.AppStashClassLoading;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.util.Date;

/**
 * @author zutherb
 */
public class ClassLoadingLoggingTask extends AbstractLoggingTask<AppStashClassLoading> implements Runnable {

    private static final String CLASS_LOADING = "ClassLoading";

    @Override
    public void run() {
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        AppStashClassLoading appStashClassLoading = AppStashClassLoading.builder()
                .name(classLoadingMXBean.getObjectName().getCanonicalName())
                .type(CLASS_LOADING)
                .host(getHostName())
                .ip(getHostAddress())
                .timestamp(new Date())
                .loadedClassCount(classLoadingMXBean.getLoadedClassCount())
                .totalLoadedClassCount(classLoadingMXBean.getTotalLoadedClassCount())
                .unloadedClassCount(classLoadingMXBean.getUnloadedClassCount())
                .create();
        log(appStashClassLoading);
    }

    @Override
    protected String getTypeName() {
        return "classes";
    }
}
