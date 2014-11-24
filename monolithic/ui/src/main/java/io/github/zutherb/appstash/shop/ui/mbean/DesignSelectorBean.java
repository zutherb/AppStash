package io.github.zutherb.appstash.shop.ui.mbean;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component("designSelector")
@ManagedResource(objectName = "io.github.zutherb.appstash.ui:name=DesignSelector")
public class DesignSelectorBean {

    private static final AtomicInteger DESIGN_COUNTER = new AtomicInteger();
    private DesignType designType = DesignType.standard;

    @ManagedOperation
    public synchronized String getDesignType() {
        return designType.name();
    }

    @ManagedOperation
    public synchronized void setDesignType(String name) {
        this.designType = DesignType.fromName(name);
    }

    @ManagedOperation
    public List<String> getAvailableDesignTypes() {
        return DesignType.names();
    }

    @ManagedOperation
    public synchronized void standard() {
        designType = DesignType.standard;
    }

    @ManagedOperation
    public synchronized void next() {
        designType = DesignType.values()[DESIGN_COUNTER.incrementAndGet() % DesignType.values().length];
    }
}
