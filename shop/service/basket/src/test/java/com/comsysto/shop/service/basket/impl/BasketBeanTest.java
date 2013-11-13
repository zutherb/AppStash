package com.comsysto.shop.service.basket.impl;

import com.comsysto.common.util.SeoUtils;
import com.comsysto.shop.repository.product.model.ProductType;
import com.comsysto.shop.service.basket.api.Basket;
import com.comsysto.shop.service.basket.model.BasketItem;
import com.comsysto.shop.service.product.model.ProductInfo;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.*;

/**
 * @author zutherb
 */
public class BasketBeanTest {

    private Basket basket;

    @Before
    public void setUp() throws Exception {
        basket = new BasketBean();
        basket.addItem( new ProductInfo(new ObjectId().toString(), "A1", "Salami", SeoUtils.urlFriendly("Salami"), "", ProductType.PIZZA, 2.0,"category") );
    }

    @Test
    public void testAddItem() throws Exception {
        assertFalse(basket.isEmpty());
        assertEquals(2.0, basket.getTotalSum());
    }

    @Test
    public void testRemoveItem() throws Exception {
        List<BasketItem> basketItems = basket.getAll();
        assertEquals(1, basketItems.size());
        basket.removeItem(basketItems.get(0));
        assertTrue(basket.isEmpty());
    }

    @Test
    public void testClearAll() throws Exception {
        basket.clearAll();
        assertTrue(basket.isEmpty());
    }
}
