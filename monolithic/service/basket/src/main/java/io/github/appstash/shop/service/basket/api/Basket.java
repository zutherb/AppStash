package io.github.appstash.shop.service.basket.api;

import io.github.appstash.shop.service.basket.model.BasketItem;
import io.github.appstash.shop.service.product.model.ProductInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zutherb
 */
public interface Basket {
    BasketItem addItem(ProductInfo product);
    boolean removeItem(BasketItem item);
    List<BasketItem> getAll();
    void clearAll();
    boolean isEmpty();
    BigDecimal getTotalSum();
}
