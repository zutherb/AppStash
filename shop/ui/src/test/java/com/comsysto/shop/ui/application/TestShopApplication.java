package com.comsysto.shop.ui.application;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.ApplicationContext;

/**
 * @author zutherb
 */
public class TestShopApplication extends ShopApplication {

    private final ApplicationContext applicationContext;

    public TestShopApplication(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    protected void init() {
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContext, true));
    }
}
