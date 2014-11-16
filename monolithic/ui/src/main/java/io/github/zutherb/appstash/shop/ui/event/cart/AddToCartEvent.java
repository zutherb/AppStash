package io.github.zutherb.appstash.shop.ui.event.cart;


import io.github.zutherb.appstash.shop.service.cart.api.Cart;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;


public class AddToCartEvent implements CartChangeEvent {
    private final AjaxRequestTarget target;

    @SpringBean
    private Cart cart;

    public AddToCartEvent(AjaxRequestTarget target, Component component, ProductInfo product, List<String> tags) {
        this.target = target;
        Injector.get().inject(this);
        cart.addItem(product);
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }
}
