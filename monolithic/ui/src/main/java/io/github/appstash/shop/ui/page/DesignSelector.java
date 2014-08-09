package io.github.appstash.shop.ui.page;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ManagedResource(objectName = "io.github.appstash.ui:name=DesignSelector")
public class DesignSelector {

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
    public synchronized void next() {
        designType = DesignType.values()[DESIGN_COUNTER.incrementAndGet() % DesignType.values().length];
    }
}
