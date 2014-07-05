package io.github.appstash.shop.service.order.model;

import io.github.appstash.shop.service.product.model.ProductInfo;

import java.io.Serializable;

/**
 * @author zutherb
 */
public class OrderItemInfo implements Serializable {

    private ProductInfo product;
    private String uuid;

    private OrderItemInfo() {
    }

    public OrderItemInfo(ProductInfo product, String uuid) {
        this.product = product;
        this.uuid = uuid;
    }

    public ProductInfo getProduct() {
        return product;
    }

    public Double getTotalSum() {
        Double totalSum = 0.0;
        totalSum += product.getPrice();
        return totalSum;
    }

    public String getUuid() {
        return uuid;
    }
}
