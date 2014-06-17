package io.github.appstash.memoryleak.printer;

import java.lang.management.MemoryPoolMXBean;

/**
 * @author zutherb
 */
public class MemoryProgressPrinterContext extends AbstractPrinterContext {

    private static final Object LOCK = new Object();

    private final MemoryPoolMXBean mxBean;

    public MemoryProgressPrinterContext(MemoryPoolMXBean mxBean) {
        synchronized (LOCK) {
            int maxLength = mxBean.getName().length();
            if (STRING_LENGTH_COUNTER.get() < maxLength) {
                STRING_LENGTH_COUNTER.set(maxLength);
            }
        }
        this.mxBean = mxBean;
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

    @Override
    protected int getLength() {
        return mxBean.getName().length();
    }
}
