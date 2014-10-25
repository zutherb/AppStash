package io.github.appstash.shop.service.cart.impl;

import io.github.appstash.shop.service.cart.model.CartItemInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by berndzuther on 19.10.14.
 */
public abstract class AbstractFulfillmentProvider {


    public BigDecimal getTotalSum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (CartItemInfo cartItemInfo : getItems()) {
            sum = sum.add(cartItemInfo.getTotalSum());
        }
        return sum;
    }

    public abstract List<CartItemInfo> getItems();
}
