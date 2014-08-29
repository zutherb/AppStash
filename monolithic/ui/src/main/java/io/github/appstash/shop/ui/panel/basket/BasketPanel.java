package io.github.appstash.shop.ui.panel.basket;


import io.github.appstash.shop.service.basket.api.Basket;
import io.github.appstash.shop.service.basket.model.BasketItem;
import io.github.appstash.shop.ui.event.basket.BasketChangeEvent;
import io.github.appstash.shop.ui.event.basket.RemoveFromBasketEvent;
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

public class BasketPanel extends AbstractShopBasePanel {

    @SpringBean(name = "basket")
    private Basket basket;

    public BasketPanel(String id) {
        super(id);

        add(totalSum());
        add(checkoutLink());
        add(basketView());

        setOutputMarkupId(true);

        add(new HighLightBehavior());
    }

    private Label totalSum() {
        return new Label("price", new PriceModel(new PropertyModel<>(basket, "totalSum")));
    }

    private BookmarkablePageLink<Void> checkoutLink() {
        return new BookmarkablePageLink<>("checkout", CheckoutPage.class);
    }

    private Component basketView() {
        DataView<BasketItem> basketView = new DataView<BasketItem>("basket", basketDataProvider()) {
            @Override
            protected void populateItem(final Item<BasketItem> item) {
                WebMarkupContainer basketItem = new WebMarkupContainer("item");
                basketItem.add(new Label("name", new PropertyModel<String>(item.getModel(), "product.name")));
                basketItem.add(new IndicatingAjaxLink<Void>("delete") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        send(BasketPanel.this, Broadcast.BREADTH, new RemoveFromBasketEvent(item.getModel().getObject(), target));
                    }
                });
                basketItem.add(new Label("price", new PriceModel(new PropertyModel<>(item.getModel(), "totalSum"))));
                item.add(basketItem);
            }
        };
        basketView.setOutputMarkupId(true);
        return basketView;
    }

    private ListDataProvider<BasketItem> basketDataProvider() {
        return new ListDataProvider<>(basket.getAll());
    }

    @Override
    public void onEvent(IEvent<?> event) {
        if (event.getPayload() instanceof BasketChangeEvent) {
            ((BasketChangeEvent) event.getPayload()).getTarget().add(this);
        }
    }
}

