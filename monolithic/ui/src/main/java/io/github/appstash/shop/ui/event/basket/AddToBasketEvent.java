package io.github.appstash.shop.ui.event.basket;


import io.github.appstash.shop.service.basket.api.Basket;
import io.github.appstash.shop.service.basket.model.BasketItem;
import io.github.appstash.shop.service.product.model.ProductInfo;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;


public class AddToBasketEvent implements BasketChangeEvent {
    private final AjaxRequestTarget target;

    @SpringBean
    private Basket basket;

    public AddToBasketEvent(AjaxRequestTarget target, Component component, ProductInfo product, List<String> tags) {
        this.target = target;
        Injector.get().inject(this);
        basket.addItem(product);
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }
}
