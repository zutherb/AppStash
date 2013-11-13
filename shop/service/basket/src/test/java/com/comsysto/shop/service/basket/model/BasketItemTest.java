package com.comsysto.shop.service.basket.model;

import com.comsysto.common.util.SeoUtils;
import com.comsysto.shop.repository.product.model.ProductType;
import com.comsysto.shop.service.product.model.ProductInfo;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author zutherb
 */
public class BasketItemTest {

    private BasketItem basketItem;

    @Before
    public void setup() {
        basketItem = new BasketItem(new ProductInfo(new ObjectId().toString(), "B2", "Salami", SeoUtils.urlFriendly("Salami"), "", ProductType.PIZZA, 2.5, "category"));
    }

    @Test
    public void testGetTotalSum() {
        assertEquals(2.5, basketItem.getTotalSum());
    }
}
