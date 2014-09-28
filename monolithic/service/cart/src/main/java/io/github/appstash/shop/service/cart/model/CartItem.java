package io.github.appstash.shop.service.cart.model;

import io.github.appstash.shop.service.product.model.ProductInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author zutherb
 */
public class CartItem implements Serializable {

    private String uuid;
    private ProductInfo productInfo;

    public CartItem(ProductInfo productInfo) {
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

        CartItem that = (CartItem) o;

        if (!uuid.equals(that.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    public BigDecimal getTotalSum() {
        BigDecimal sum = BigDecimal.ZERO;
        sum = sum.add(productInfo.getPrice());
        return sum;
    }
}
