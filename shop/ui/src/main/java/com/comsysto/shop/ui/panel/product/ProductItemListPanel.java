package com.comsysto.shop.ui.panel.product;

import com.comsysto.shop.service.product.model.ProductInfo;
import com.comsysto.shop.ui.panel.base.AbstractPizzaShopBasePanel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductItemListPanel extends AbstractPizzaShopBasePanel {

    private IModel<?> containerTopic;
    private IModel<List<ProductInfo>> productListModel;

    public ProductItemListPanel(String id, String recommenderType, IModel<?> containerTopic, IModel<List<ProductInfo>> productListModel) {

        super(id, productListModel);

        this.containerTopic = containerTopic;
        this.productListModel = productListModel;

        add(topicLabel());
        add(productList(recommenderType));

        setOutputMarkupId(true);
    }

    @Override
    protected void onConfigure() {
        setVisible(CollectionUtils.isNotEmpty(productListModel.getObject()));
    }

    private Label topicLabel() {
        return new Label("productsTopic", containerTopic);
    }

    private DataView<ProductInfo> productList(final String parentTag) {
        return new DataView<ProductInfo>("products", productsProvider()) {
            @Override
            protected void populateItem(Item<ProductInfo> item) {
                item.add(newProductItemPanel("product", parentTag, item.getModel()));
            }
        };
    }

    protected Component newProductItemPanel(String id, String parentTag, IModel<ProductInfo> model) {
        return new ProductItemPanel(id, model, createTags(parentTag));
    }

    private IDataProvider<ProductInfo> productsProvider() {
        return new IDataProvider<ProductInfo>() {
            @Override
            public Iterator<ProductInfo> iterator(long first, long count) {
                return productListModel.getObject().iterator();
            }

            @Override
            public long size() {
                return productListModel.getObject().size();
            }

            @Override
            public IModel<ProductInfo> model(ProductInfo product) {
                return Model.of(product);
            }

            @Override
            public void detach() {
            }
        };
    }


    protected List<String> getTags(String parentTag) {
        return new ArrayList<String>();
    }

    protected ListModel<String> createTags(String parentTag) {
        ArrayList<String> tagList = new ArrayList<String>(1);
        tagList.add(parentTag);
        return new ListModel<String>(tagList);
    }
}
