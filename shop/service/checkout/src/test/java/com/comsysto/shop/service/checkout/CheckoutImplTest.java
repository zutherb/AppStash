package com.comsysto.shop.service.checkout;

import com.comsysto.shop.repository.product.model.ProductType;
import com.comsysto.shop.service.basket.api.Basket;
import com.comsysto.shop.service.basket.model.BasketItem;
import com.comsysto.shop.service.checkout.api.Checkout;
import com.comsysto.shop.service.checkout.impl.CheckoutImpl;
import com.comsysto.shop.service.order.model.OrderItemInfo;
import com.comsysto.shop.service.product.model.ProductInfo;
import org.bson.types.ObjectId;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author zutherb
 */
public class CheckoutImplTest {

    @Mock
    Basket basket;
    private Checkout checkout;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(basket.getAll()).thenReturn(createBasketItems());
        when(basket.getTotalSum()).thenReturn(2.0);
        checkout = new CheckoutImpl(basket, new DozerBeanMapper(Arrays.asList("com/comsysto/shop/service/checkout/dozer-mapping.xml")));
    }

    private List<BasketItem> createBasketItems() {
        return Arrays.asList(new BasketItem( new ProductInfo(new ObjectId().toString(), "1", "test", "test", "null", ProductType.PIZZA, 3.5,"test") ));
    }

    @Test
    public void testGetOrderItemInfos() throws Exception {
        List<OrderItemInfo> orderItemInfos = checkout.getOrderItemInfos();
        assertEquals(1, orderItemInfos.size());
    }

    @Test
    public void testGetTotalSum() throws Exception {
        assertEquals(2.0, checkout.getTotalSum());
    }
}
