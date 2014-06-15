package io.github.appstash.memoryleak.printer;

import java.lang.management.MemoryPoolMXBean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zutherb
 */
public class MemoryProgressPrinterContext {

    private static final Object LOCK = new Object();

    private static final AtomicInteger INSTANCE_COUNTER = new AtomicInteger();
    private static final AtomicInteger STRING_LENGTH_COUNTER = new AtomicInteger();

    private final MemoryPoolMXBean mxBean;
    private final int counter;
    private String returning;

    public MemoryProgressPrinterContext(MemoryPoolMXBean mxBean) {
        synchronized (LOCK) {
            int maxLength = mxBean.getName().length();
            if (STRING_LENGTH_COUNTER.get() < maxLength) {
                STRING_LENGTH_COUNTER.set(maxLength);
            }
        }
        this.mxBean = mxBean;
        this.counter = INSTANCE_COUNTER.incrementAndGet();
    }

    public double getUsed() {
        return mxBean.getUsage().getUsed();
    }

    public double getMax() {
        return mxBean.getUsage().getMax();
    }

    public String getName() {
        return mxBean.getName() + getSpaces();
    }

    private String getSpaces() {
        StringBuilder builder = new StringBuilder();
        for (int i = mxBean.getName().length(); i < STRING_LENGTH_COUNTER.get(); i++) {
            builder.append(" ");
        }
        return builder.toString();
    }
}
