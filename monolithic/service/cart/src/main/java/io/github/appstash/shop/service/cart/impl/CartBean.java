package io.github.appstash.shop.service.cart.impl;

import io.github.appstash.shop.service.cart.api.Cart;
import io.github.appstash.shop.service.cart.model.CartItem;
import io.github.appstash.shop.service.cart.api.Cart;
import io.github.appstash.shop.service.product.model.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zutherb
 */
@Component("cart")
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class CartBean implements Cart {

    private Logger logger = LoggerFactory.getLogger(CartBean.class);

    private List<CartItem> items;

    public CartBean(){
        items = new ArrayList<>();
    }

    @Override
    public CartItem addItem(ProductInfo productInfo) {
        CartItem cartItem = new CartItem(productInfo);
        items.add(cartItem);
        return cartItem;
    }

    @Override
    public boolean removeItem(CartItem item) {
        return items.remove(item);
    }

    @Override
    public List<CartItem> getAll() {
        return items;
    }

    @Override
    public void clearAll(){
        items.clear();
        logger.info("Cart was cleared");
    }

    @Override
    public boolean isEmpty(){
        return items.isEmpty();
    }

    @Override
    public BigDecimal getTotalSum(){
        BigDecimal sum = BigDecimal.ZERO;
        for (CartItem cartItem : items){
            sum = sum.add(cartItem.getTotalSum());
        }
        return sum;
    }

}
