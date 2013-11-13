package com.comsysto.shop.service.checkout.api;

import com.comsysto.shop.service.order.model.OrderItemInfo;

import java.util.List;

/**
 * @author zutherb
 */
public interface Checkout {
    List<OrderItemInfo> getOrderItemInfos();
    Double getTotalSum();
}
