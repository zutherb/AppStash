package io.github.appstash.shop.ui.panel.product;

import io.github.appstash.shop.service.product.model.ProductInfo;
import io.github.appstash.shop.ui.panel.base.HighLightBehavior;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author zutherb
 */
public class RecommendationItemListPanel extends ProductItemListPanel {

    public RecommendationItemListPanel(String id, String recommenderType, IModel<?> containerTopic, IModel<List<ProductInfo>> productListModel) {
        super(id, recommenderType, containerTopic, productListModel);
    }

    @Override
    protected Component newProductItemPanel(String id, String parentTag, IModel<ProductInfo> model) {
        return new RecommendationItemPanel(id, model, createTags(parentTag));
    }
}
