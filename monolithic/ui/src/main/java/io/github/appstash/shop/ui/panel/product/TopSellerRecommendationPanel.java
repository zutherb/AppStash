package io.github.appstash.shop.ui.panel.product;

import io.github.appstash.shop.repository.product.model.ProductType;
import io.github.appstash.shop.service.product.model.ProductInfo;
import io.github.appstash.shop.service.recommendation.api.RecommendationService;
import io.github.appstash.shop.ui.event.AjaxEvent;
import io.github.appstash.shop.ui.event.basket.AddToBasketEvent;
import io.github.appstash.shop.ui.event.basket.RemoveFromBasketEvent;
import io.github.appstash.shop.ui.mbean.FeatureTooglesBean;
import io.github.appstash.shop.ui.panel.base.AbstractPizzaShopBasePanel;
import io.github.appstash.shop.ui.panel.base.HighLightBehavior;
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

    @SpringBean
    private FeatureTooglesBean featureTooglesBean;

    private IModel<ProductType> productTypeModel;
    private IModel<ProductInfo> pizzaInfoModel;

    public TopSellerRecommendationPanel(String id, IModel<ProductType> productTypeModel) {
        super(id);

        this.productTypeModel = productTypeModel;

        pizzaInfoModel = productInfoModel();
        add(recommendationItemPanel());
        setOutputMarkupId(true);
        setOutputMarkupPlaceholderTag(true);
        add(new HighLightBehavior());
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
        setVisible(featureTooglesBean.isTopSellerFeatureEnabled() && pizzaInfoModel.getObject() != null);
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
