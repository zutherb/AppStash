package com.comsysto.shop.service.basket.api;

import com.comsysto.shop.service.basket.model.BasketItem;
import com.comsysto.shop.service.product.model.ProductInfo;

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

    double getTotalSum();
}
