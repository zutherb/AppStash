package io.github.appstash.shop.ui.event.cart;


import io.github.appstash.shop.service.cart.api.Cart;
import io.github.appstash.shop.service.cart.model.CartItem;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class RemoveFromCartEvent implements CartChangeEvent {

    private final AjaxRequestTarget target;

    @SpringBean
    private Cart cart;

    public RemoveFromCartEvent(CartItem cartItem, AjaxRequestTarget target) {
        this.target = target;
        Injector.get().inject(this);
        cart.removeItem(cartItem);
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }
}
