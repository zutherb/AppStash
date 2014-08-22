package io.github.appstash.shop.ui.mbean;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName = "io.github.appstash.ui:name=FeatureToogles")
public class FeatureTooglesBean {

    private boolean topSellerFeatureEnabled = true;
    private boolean highlightingFeatureEnabled = false;

    @ManagedOperation
    public synchronized void disableTopSellerFeature() {
        topSellerFeatureEnabled = false;
    }

    @ManagedOperation
    public synchronized void enableTopSellerFeature() {
        topSellerFeatureEnabled = true;
    }

    @ManagedOperation
    public synchronized boolean isTopSellerFeatureEnabled() {
        return topSellerFeatureEnabled;
    }

    @ManagedOperation
    public synchronized void disableHighlightingFeature() {
        highlightingFeatureEnabled = false;
    }

    @ManagedOperation
    public synchronized void enableHighlightingFeature() {
        highlightingFeatureEnabled = true;
    }

    @ManagedOperation
    public boolean isHighlightingFeatureEnabled() {
        return highlightingFeatureEnabled;
    }

    @ManagedOperation
    public void toogleHighlightingFeature() {
        highlightingFeatureEnabled = !highlightingFeatureEnabled;
    }

    @ManagedOperation
    public void toogleTopSellerFeature() {
        topSellerFeatureEnabled = !topSellerFeatureEnabled;
    }
}
