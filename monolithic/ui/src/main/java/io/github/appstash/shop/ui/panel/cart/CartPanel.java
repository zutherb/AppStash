package io.github.appstash.shop.ui.panel.cart;


import io.github.appstash.shop.service.cart.api.Cart;
import io.github.appstash.shop.service.cart.model.CartItem;
import io.github.appstash.shop.ui.event.cart.CartChangeEvent;
import io.github.appstash.shop.ui.event.cart.RemoveFromCartEvent;
import io.github.appstash.shop.ui.model.PriceModel;
import io.github.appstash.shop.ui.page.checkout.CheckoutPage;
import io.github.appstash.shop.ui.panel.base.AbstractShopBasePanel;
import io.github.appstash.shop.ui.panel.base.HighLightBehavior;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class CartPanel extends AbstractShopBasePanel {

    @SpringBean(name = "cart")
    private Cart cart;

    public CartPanel(String id) {
        super(id);

        add(totalSum());
        add(checkoutLink());
        add(cartView());

        setOutputMarkupId(true);

        add(new HighLightBehavior());
    }

    private Label totalSum() {
        return new Label("price", new PriceModel(new PropertyModel<>(cart, "totalSum")));
    }

    private BookmarkablePageLink<Void> checkoutLink() {
        return new BookmarkablePageLink<>("checkout", CheckoutPage.class);
    }

    private Component cartView() {
        DataView<CartItem> cartView = new DataView<CartItem>("cart", cartDataProvider()) {
            @Override
            protected void populateItem(final Item<CartItem> item) {
                WebMarkupContainer cartItem = new WebMarkupContainer("item");
                cartItem.add(new Label("name", new PropertyModel<String>(item.getModel(), "product.name")));
                cartItem.add(new IndicatingAjaxLink<Void>("delete") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        send(CartPanel.this, Broadcast.BREADTH, new RemoveFromCartEvent(item.getModel().getObject(), target));
                    }
                });
                cartItem.add(new Label("price", new PriceModel(new PropertyModel<>(item.getModel(), "totalSum"))));
                item.add(cartItem);
            }
        };
        cartView.setOutputMarkupId(true);
        return cartView;
    }

    private ListDataProvider<CartItem> cartDataProvider() {
        return new ListDataProvider<>(cart.getAll());
    }

    @Override
    public void onEvent(IEvent<?> event) {
        if (event.getPayload() instanceof CartChangeEvent) {
            ((CartChangeEvent) event.getPayload()).getTarget().add(this);
        }
    }
}

