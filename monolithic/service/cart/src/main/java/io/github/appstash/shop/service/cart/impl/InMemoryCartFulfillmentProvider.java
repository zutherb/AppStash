package io.github.appstash.shop.service.cart.impl;

import io.github.appstash.shop.service.cart.api.CartFulfillmentProvider;
import io.github.appstash.shop.service.cart.model.CartItem;
import io.github.appstash.shop.service.product.model.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zutherb
 */
@Component("inMemoryCart")
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class InMemoryCartFulfillmentProvider extends AbstractFulfillmentProvider implements CartFulfillmentProvider {

    private Logger logger = LoggerFactory.getLogger(InMemoryCartFulfillmentProvider.class);

    private List<CartItem> items;

    public InMemoryCartFulfillmentProvider() {
        items = new ArrayList<>();
    }

    @Override
    public CartItem addItem(ProductInfo productInfo) {
        CartItem cartItem = new CartItem(productInfo);
        getItems().add(cartItem);
        return cartItem;
    }

    @Override
    public boolean removeItem(CartItem item) {
        return getItems().remove(item);
    }

    @Override
    public List<CartItem> getAll() {
        return getItems();
    }

    @Override
    public void clearAll() {
        getItems().clear();
        logger.info("Cart was cleared");
    }

    @Override
    public boolean isEmpty() {
        return getItems().isEmpty();
    }

    @Override
    public List<CartItem> getItems() {
        return items;
    }
}
