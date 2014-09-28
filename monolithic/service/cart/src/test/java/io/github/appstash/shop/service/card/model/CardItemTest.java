package io.github.appstash.shop.service.cart.model;

import io.github.appstash.common.util.SeoUtils;
import io.github.appstash.shop.repository.product.model.ProductType;
import io.github.appstash.shop.service.product.model.ProductInfo;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

/**
 * @author zutherb
 */
public class CartItemTest {

    private CartItem cartItem;

    @Before
    public void setup() {
        cartItem = new CartItem(new ProductInfo(new ObjectId().toString(), "B2", "Salami", SeoUtils.urlFriendly("Salami"), "", ProductType.HANDY, 2.5, "category"));
    }

    @Test
    public void testGetTotalSum() {
        assertEquals(BigDecimal.valueOf(2.5), cartItem.getTotalSum());
    }
}
