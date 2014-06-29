package io.github.appstash.shop.service.basket.model;

import io.github.appstash.shop.service.product.model.ProductInfo;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author zutherb
 */
public class BasketItem implements Serializable {

    private String uuid;
    private ProductInfo productInfo;

    public BasketItem(ProductInfo productInfo) {
        this.productInfo = productInfo;
        uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public ProductInfo getProduct() {
        return productInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasketItem that = (BasketItem) o;

        if (!uuid.equals(that.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    public double getTotalSum() {
        double sum = 0;
        sum += productInfo.getPrice();
        return sum;
    }
}
