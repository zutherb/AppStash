package io.github.appstash.shop.service.cart.impl;

import io.github.appstash.shop.service.cart.api.Cart;
import io.github.appstash.shop.service.cart.api.CartFulfillmentProvider;
import io.github.appstash.shop.service.cart.model.CartItem;
import io.github.appstash.shop.service.product.model.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component("cart")
@ManagedResource(objectName = "io.github.appstash.shop.service.cart:name=CartResolver")
public class CartResolver extends InMemoryCartFulfillmentProvider implements Cart {

    private final List<CartFulfillmentProvider> cartFulfillmentProviders;

    private CartFulfillmentProvider delegate;

    @Autowired
    public CartResolver(List<CartFulfillmentProvider> cartFulfillmentProviders) {
        Assert.notEmpty(cartFulfillmentProviders, "No CartFulfillmentProviders found");
        this.cartFulfillmentProviders = Collections.unmodifiableList(cartFulfillmentProviders);
        delegate = this.cartFulfillmentProviders.get(0);
    }

    @Override
    public CartItem addItem(ProductInfo product) {
        return delegate.addItem(product);
    }

    @Override
    public boolean removeItem(CartItem item) {
        return delegate.removeItem(item);
    }

    @Override
    public List<CartItem> getAll() {
        return delegate.getAll();
    }

    @Override
    public void clearAll() {
        delegate.clearAll();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public BigDecimal getTotalSum() {
        return delegate.getTotalSum();
    }

    @ManagedOperation
    public String getActiveCartFulfillmentProviderName(){
        return delegate.getClass().getSimpleName();
    }
}
