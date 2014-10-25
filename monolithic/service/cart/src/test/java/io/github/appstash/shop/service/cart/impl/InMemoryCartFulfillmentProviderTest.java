package io.github.appstash.shop.service.cart.impl;

import io.github.appstash.common.util.SeoUtils;
import io.github.appstash.shop.repository.product.model.ProductType;
import io.github.appstash.shop.service.cart.api.CartFulfillmentProvider;
import io.github.appstash.shop.service.cart.model.CartItemInfo;
import io.github.appstash.shop.service.product.model.ProductInfo;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * @author zutherb
 */
public class InMemoryCartFulfillmentProviderTest {

    private CartFulfillmentProvider cart;

    @Before
    public void setUp() throws Exception {
        cart = new InMemoryCartFulfillmentProvider();
        cart.addItem(new ProductInfo(new ObjectId().toString(), "A1", "Salami", SeoUtils.urlFriendly("Salami"), "", ProductType.HANDY, 2.0, "category"));
    }

    @Test
    public void testAddItem() throws Exception {
        assertFalse(cart.isEmpty());
        assertEquals(BigDecimal.valueOf(2.0), cart.getTotalSum());
    }

    @Test
    public void testRemoveItem() throws Exception {
        List<CartItemInfo> cartItemInfos = cart.getAll();
        assertEquals(1, cartItemInfos.size());
        cart.removeItem(cartItemInfos.get(0));
        assertTrue(cart.isEmpty());
    }

    @Test
    public void testClearAll() throws Exception {
        cart.clearAll();
        assertTrue(cart.isEmpty());
    }
}