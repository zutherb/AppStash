package io.github.appstash.task;

import io.github.appstash.model.AppStashClassLoading;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

/**
 * @author zutherb
 */
public class CpuLoggingTask extends AbstractLoggingTask implements Runnable {


    @Override
    public void run() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        try {
            getRestTemplate().put("http://10.211.55.100:9200/analytics/cpu/{id}",
                    AppStashClassLoading.builder()
                            .host(getHostName())
                            .ip(getHostAddress())
                            .timestamp(new Date())
                            .create(),
                    createRandomUUID()
            );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    private String createRandomUUID() {
        return UUID.randomUUID().toString();
    }

    private String getHostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    private String getHostAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
