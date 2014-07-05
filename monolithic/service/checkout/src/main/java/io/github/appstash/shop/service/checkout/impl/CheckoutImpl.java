package io.github.appstash.shop.service.checkout.impl;

import io.github.appstash.shop.service.basket.api.Basket;
import io.github.appstash.shop.service.basket.model.BasketItem;
import io.github.appstash.shop.service.checkout.api.Checkout;
import io.github.appstash.shop.service.order.model.OrderItemInfo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zutherb
 */
@Component("checkout")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.INTERFACES)
public class CheckoutImpl implements Checkout {

    private Basket basket;
    private Mapper mapper;

    @Autowired
    public CheckoutImpl(@Qualifier("basket") Basket basket,
                        @Qualifier("dozerMapper") Mapper mapper) {
        this.basket = basket;
        this.mapper = mapper;
    }

    @Override
    public List<OrderItemInfo> getOrderItemInfos() {
        ArrayList<OrderItemInfo> orderItemInfos = new ArrayList<>();
        for (BasketItem basketItem : basket.getAll()) {
            orderItemInfos.add(mapper.map(basketItem, OrderItemInfo.class));
        }
        return orderItemInfos;
    }

    @Override
    public Double getTotalSum() {
        return basket.getTotalSum();
    }
}
