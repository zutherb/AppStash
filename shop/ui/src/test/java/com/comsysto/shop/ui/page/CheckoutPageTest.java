package com.comsysto.shop.ui.page;

import com.comsysto.shop.ui.AbstractWicketTest;
import com.comsysto.shop.ui.page.checkout.CheckoutPage;
import org.junit.Ignore;
import org.junit.Test;

import static com.comsysto.shop.service.TestDataFactory.createOrderItems;
import static org.mockito.Mockito.when;

/**
 * @author zutherb
 */
public class CheckoutPageTest extends AbstractWicketTest {

    @Test
    @Ignore
    public void testPizzaListPage(){
        wicketTester.startPage(CheckoutPage.class);
        wicketTester.assertRenderedPage(CheckoutPage.class);
    }

    @Override
    public void initMockData() {
        when(authenticationService.isAuthorized()).thenReturn(true);
        when(basket.isEmpty()).thenReturn(false);
        when(checkout.getOrderItemInfos()).thenReturn(createOrderItems());
    }
}
