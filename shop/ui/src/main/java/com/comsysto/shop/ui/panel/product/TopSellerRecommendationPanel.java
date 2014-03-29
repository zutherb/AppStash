package com.comsysto.shop.ui.panel.product;

import com.comsysto.shop.ui.event.AjaxEvent;
import com.comsysto.shop.repository.product.model.ProductType;
import com.comsysto.shop.service.product.model.ProductInfo;
import com.comsysto.shop.service.recommendation.api.RecommendationService;
import com.comsysto.shop.ui.event.basket.AddToBasketEvent;
import com.comsysto.shop.ui.event.basket.RemoveFromBasketEvent;
import com.comsysto.shop.ui.panel.base.AbstractPizzaShopBasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zutherb
 */
public class TopSellerRecommendationPanel extends AbstractPizzaShopBasePanel {

    @SpringBean(name = "recommendationService")
    private RecommendationService recommendationService;

    private IModel<ProductType> productTypeModel;
    private IModel<ProductInfo> pizzaInfoModel;

    public TopSellerRecommendationPanel(String id, IModel<ProductType> productTypeModel) {
        super(id);

        this.productTypeModel = productTypeModel;

        pizzaInfoModel = productInfoModel();
        add(recommendationItemPanel());
        setOutputMarkupId(true);
        setOutputMarkupPlaceholderTag(true);
    }

    private Component recommendationItemPanel() {
        ArrayList<String> tagListe = new ArrayList<>(2);
        IModel<List<String>> tagsModel = new ListModel<>(tagListe);
        RecommendationItemPanel recommendationItemPanel = new RecommendationItemPanel("recPizzaItem", pizzaInfoModel, tagsModel);
        recommendationItemPanel.setOutputMarkupId(true);
        return recommendationItemPanel;
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        setVisible(pizzaInfoModel.getObject() != null);
    }


    private IModel<ProductInfo> productInfoModel() {
        return new LoadableDetachableModel<ProductInfo>() {
            @Override
            protected ProductInfo load() {

                List<ProductInfo> products = recommendationService.getTopsellerRecommendations(productTypeModel.getObject(), 1);
                return products.size() > 0 ? products.get(0) : null;
            }
        };
    }

    @Override
    public void onEvent(IEvent<?> event) {
        if (event.getPayload() instanceof AddToBasketEvent || event.getPayload() instanceof RemoveFromBasketEvent) {
            ((AjaxEvent) event.getPayload()).getTarget().add(this);
        }
    }
}
