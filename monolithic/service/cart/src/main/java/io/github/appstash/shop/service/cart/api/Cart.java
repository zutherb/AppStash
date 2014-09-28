package io.github.appstash.shop.service.cart.api;

import io.github.appstash.shop.service.cart.model.CartItem;
import io.github.appstash.shop.service.product.model.ProductInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zutherb
 */
public interface Cart {
    CartItem addItem(ProductInfo product);
    boolean removeItem(CartItem item);
    List<CartItem> getAll();
    void clearAll();
    boolean isEmpty();
    BigDecimal getTotalSum();
}
