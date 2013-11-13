package com.comsysto.shop.ui;

import com.comsysto.shop.ui.navigation.NavigationEntry;
import com.comsysto.shop.ui.navigation.NavigationGroup;
import com.comsysto.shop.ui.navigation.Navigation;
import com.comsysto.shop.ui.navigation.NavigationProvider;
import com.comsysto.shop.service.authentication.api.AuthenticationService;
import com.comsysto.shop.service.authentication.api.FakeAuthenticationService;
import com.comsysto.shop.service.user.api.UserService;
import com.comsysto.shop.repository.product.api.ProductRepository;
import com.comsysto.shop.service.basket.api.Basket;
import com.comsysto.shop.service.checkout.api.Checkout;
import com.comsysto.shop.service.product.api.ProductService;
import com.comsysto.shop.service.recommendation.api.RecommendationService;
import com.comsysto.shop.ui.application.TestShopApplication;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author zutherb
 */
public abstract class AbstractWicketTest {

    protected WicketTester wicketTester;

    @Mock
    protected AuthenticationService authenticationService;
    @Mock
    protected Basket basket;
    @Mock
    protected Checkout checkout;
    @Mock
    protected ProductRepository productRepository;
    @Mock
    protected ProductService productService;
    @Mock
    protected RecommendationService recommendationService;
    @Mock
    protected RestTemplate restTemplate;
    @Mock
    protected FakeAuthenticationService fakeAuthenticationService;
    @Mock
    protected NavigationProvider navigationProvider;
    @Mock
    protected UserService userService;

    @Before
    public void initializeWicketTester() throws Exception {
        initMocks(this);
        ApplicationContextMock appctx = initializeApplicationContext();
        TestShopApplication application = new TestShopApplication(appctx);
        wicketTester = new WicketTester(application);
    }

    private ApplicationContextMock initializeApplicationContext() {
        Navigation navigationMock = createNavigationMock();
        when(navigationProvider.getNavigation()).thenReturn(navigationMock);

        ApplicationContextMock appctx = new ApplicationContextMock();
        appctx.putBean("authenticationService", authenticationService);
        appctx.putBean("basket", basket);
        appctx.putBean("checkout", checkout);
        appctx.putBean("productRepository", productRepository);
        appctx.putBean("productService", productService);
        appctx.putBean("frequentlyBoughtWithProductsRecommendationService", recommendationService);
        appctx.putBean("restTemplate", restTemplate);
        appctx.putBean("fakeAuthenticationService", fakeAuthenticationService);
        appctx.putBean("navigationProvider", navigationProvider);
        appctx.putBean("userService", userService);
        appctx.putBean("dozerMapper", createDozerMapper());

        return appctx;
    }

    private Mapper createDozerMapper() {
        return new DozerBeanMapper(Arrays.asList("com/comsysto/shop/service/user/dozer-mapping.xml",
                "com/comsysto/shop/service/checkout/dozer-mapping.xml",
                "com/comsysto/shop/service/order/dozer-mapping.xml",
                "com/comsysto/shop/service/product/dozer-mapping.xml",
                "com/comsysto/shop/service/rule/dozer-mapping.xml",
                "com/comsysto/shop/ui/dozer-mapping.xml"));
    }

    private Navigation createNavigationMock() {
        Navigation navigation = mock(Navigation.class);
        NavigationGroup mainGroup = mock(NavigationGroup.class);
        when(mainGroup.getNavigationEntries()).thenReturn(Collections.<NavigationEntry>emptyList());
        when(navigation.getMainNavigationGroup()).thenReturn(mainGroup);
        return navigation;
    }

    @Before
    public abstract void initMockData();
}
