package io.github.appstash.shop.service.cart.impl;

import io.github.appstash.shop.service.cart.model.CartItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by berndzuther on 19.10.14.
 */
public abstract class AbstractFulfillmentProvider {


    public BigDecimal getTotalSum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (CartItem cartItem : getItems()) {
            sum = sum.add(cartItem.getTotalSum());
        }
        return sum;
    }

    public abstract List<CartItem> getItems();
}
