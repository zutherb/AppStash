package com.comsysto.shop.ui.page;

import com.comsysto.shop.ui.AbstractWicketTest;
import com.comsysto.shop.ui.page.user.LoginPage;
import org.junit.Test;

/**
 * @author zutherb
 */
public class LoginPageTest extends AbstractWicketTest {

    @Test
    public void testRenderPage(){
        wicketTester.startPage(LoginPage.class);
        wicketTester.assertRenderedPage(LoginPage.class);
    }

    @Override
    public void initMockData() {
    }
}
