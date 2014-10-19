package io.github.appstash.shop.service.cart.api;

import io.github.appstash.shop.service.cart.model.CartItem;
import io.github.appstash.shop.service.product.model.ProductInfo;

import java.math.BigDecimal;
import java.util.List;

public interface CartFulfillmentProvider {
    CartItem addItem(ProductInfo product);
    boolean removeItem(CartItem item);
    List<CartItem> getAll();
    void clearAll();
    boolean isEmpty();
    BigDecimal getTotalSum();
}
