package io.github.zutherb.appstash.shop.ui.event.cart;


import io.github.zutherb.appstash.shop.service.cart.api.Cart;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import io.github.zutherb.appstash.shop.ui.application.ShopSession;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class AddToCartEvent implements CartChangeEvent {
    private static final String DIRECT_BUY_PROCESSING_FAILED_MESSAGE = "DirectBuy processing failed";
    private static final Logger LOGGER = LoggerFactory.getLogger(AddToCartEvent.class);

    private final AjaxRequestTarget target;

    @SpringBean
    private Cart cart;

    public AddToCartEvent(AjaxRequestTarget target, Component component, ProductInfo product, List<String> tags) {
        Injector.get().inject(this);

        this.target = target;
        addProductToBasket(product);
    }

    private void addProductToBasket(ProductInfo product) {
        try {
            cart.addItem(product);
        } catch (Exception e) {
            ShopSession.get().error(DIRECT_BUY_PROCESSING_FAILED_MESSAGE);
            LOGGER.error(DIRECT_BUY_PROCESSING_FAILED_MESSAGE, e);
        }
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }
}
