package io.github.appstash.shop.service.cart.api;

import org.springframework.jmx.export.annotation.ManagedOperation;

public interface CartResolver extends Cart {
    String getActiveCartFulfillmentProviderName();
    void next();
}
