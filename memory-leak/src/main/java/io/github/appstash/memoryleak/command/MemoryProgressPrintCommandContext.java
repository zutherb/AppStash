package io.github.appstash.memoryleak.command;

import io.github.appstash.memoryleak.logging.SpaceCalculator;

import java.lang.management.MemoryPoolMXBean;

/**
 * @author zutherb
 */
public class MemoryProgressPrintCommandContext {

    private final MemoryPoolMXBean mxBean;

    public MemoryProgressPrintCommandContext(MemoryPoolMXBean mxBean) {
        this.mxBean = mxBean;
    }

    public double getUsed() {
        return mxBean.getUsage().getUsed();
    }

    public double getMax() {
        return mxBean.getUsage().getMax();
    }

    public double getCommitted() {
        return mxBean.getUsage().getCommitted();
    }

    public String getName() {
        return SpaceCalculator.appendSpaces(mxBean.getName());
    }
}
