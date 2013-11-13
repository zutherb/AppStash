package com.comsysto.shop.service.basket.impl;

import com.comsysto.shop.service.basket.api.Basket;
import com.comsysto.shop.service.basket.model.BasketItem;
import com.comsysto.shop.service.product.model.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zutherb
 */
@Component("basket")
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class BasketBean implements Basket {

    private Logger logger = LoggerFactory.getLogger(BasketBean.class);

    private List<BasketItem> items;

    public BasketBean(){
        items = new ArrayList<BasketItem>();
    }

    @Override
    public BasketItem addItem(ProductInfo productInfo) {
        BasketItem basketItem = new BasketItem(productInfo);
        items.add(basketItem);
        return basketItem;
    }

    @Override
    public boolean removeItem(BasketItem item) {
        return items.remove(item);
    }

    @Override
    public List<BasketItem> getAll() {
        return items;
    }

    @Override
    public void clearAll(){
        items.clear();
        logger.info("Basket was cleared");
    }

    @Override
    public boolean isEmpty(){
        return items.isEmpty();
    }

    @Override
    public double getTotalSum(){
        double sum = 0;
        for (BasketItem basketItem : items){
            sum += basketItem.getTotalSum();
        }
        return sum;
    }

}
