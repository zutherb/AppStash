package io.github.appstash;

import io.github.appstash.task.MemoryLoggingTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zutherb
 */
public class AppStashAgent {

    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(5);
    private static final Logger LOGGER = LoggerFactory.getLogger(AppStashAgent.class);

    public static void premain(String agentArguments, Instrumentation instrumentation) {
        agentmain(agentArguments, instrumentation);
    }

    public static void agentmain(String agentArguments, Instrumentation instrumentation) {
        SCHEDULER.scheduleAtFixedRate(new MemoryLoggingTask(), 0, 1, TimeUnit.SECONDS);
    }
}
