package io.github.appstash.shop.ui.panel.product;

import io.github.appstash.shop.service.product.model.ProductInfo;
import io.github.appstash.shop.ui.event.basket.AddToBasketEvent;
import io.github.appstash.shop.ui.model.PriceModel;
import io.github.appstash.shop.ui.page.detail.ProductDetailPage;
import io.github.appstash.shop.ui.panel.base.AbstractPizzaShopBasePanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.*;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Collections;
import java.util.List;

/**
 * @author zutherb
 */
public class ProductItemPanel extends AbstractPizzaShopBasePanel {

    private IModel<ProductInfo> productInfoModel;
    private IModel<List<String>> additionalTagsModel;
    private IModel<String> productUrlModel;

    public ProductItemPanel(String productItem, IModel<ProductInfo> model) {
        this(productItem, model, new ListModel<>(Collections.<String>emptyList()));
    }

    public ProductItemPanel(String id, IModel<ProductInfo> productInfoModel, IModel<List<String>> additionalTagsModel) {
        super(id, productInfoModel);
        this.productInfoModel = productInfoModel;
        this.additionalTagsModel = additionalTagsModel;

        add(productNameLabel());
        add(productPriceLabel());
        add(productDetailImageLink());
        add(addToBasketLink());

        setOutputMarkupId(true);
    }

    @Override
    protected void onAfterRender() {
        super.onAfterRender();
        // this is required for that the correct URL is used even if a recommendation changes while viewing the page
        productUrlModel = Model.of(productInfoModel.getObject().getUrlname());
    }


    private Label productPriceLabel() {
        return new Label("price", new PriceModel(new PropertyModel<Number>(productInfoModel, "price")));
    }

    private Label productNameLabel() {
        return new Label("name", new PropertyModel<String>(productInfoModel, "name"));
    }

    private Component productDetailImageLink() {
        Link<Void> detailPageLink = new Link<Void>("productDetailLink") {
            @Override
            public void onClick() {
                PageParameters pageParameters = new PageParameters();
                pageParameters.set("urlname", productUrlModel.getObject());
                setResponsePage(new ProductDetailPage(pageParameters));
            }
        };
        WebMarkupContainer image = new WebMarkupContainer("image");
        image.add(new AttributeModifier("src", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                String contextPath = getRequestCycle().getRequest().getContextPath();
                return contextPath + "/assets/img/Pizza/" + productInfoModel.getObject().getUrlname() + ".jpg";
            }
        }));
        image.add(new AttributeModifier("title", new PropertyModel<String>(productInfoModel, "description")));
        image.add(new AttributeModifier("alt", new PropertyModel<String>(productInfoModel, "name")));
        image.setOutputMarkupId(true);

        detailPageLink.add(image);
        return detailPageLink;
    }

    private IndicatingAjaxLink<Void> addToBasketLink() {
        IndicatingAjaxLink<Void> result = new IndicatingAjaxLink<Void>("addToBasketIcon") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                //send wicket event for add to basket
                send(getPage(), Broadcast.BREADTH, new AddToBasketEvent(target, getPage(), productInfoModel.getObject(), getTags()));
            }
        };
        result.setVisible(showAddToBasketLink());
        return result;
    }

    protected List<String> getTags() {
        return additionalTagsModel.getObject();
    }

    protected boolean showAddToBasketLink() {
        return true;
    }
}
