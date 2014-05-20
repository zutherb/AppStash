package io.github.appstash;

import io.github.appstash.task.ClassLoadingLoggingTask;
import io.github.appstash.task.MemoryLoggingTask;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zutherb
 */
public class AppStashAgent {


    public static void premain(String agentArguments, Instrumentation instrumentation) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
        scheduler.scheduleAtFixedRate(new MemoryLoggingTask(), 0, 1, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(new ClassLoadingLoggingTask(), 0, 1, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        premain(null, null);
    }
}
