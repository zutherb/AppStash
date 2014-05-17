package com.comsysto.shop.ui.page.catalog;


import com.comsysto.shop.repository.product.model.ProductType;
import com.comsysto.shop.service.product.api.ProductService;
import com.comsysto.shop.service.product.model.ProductInfo;
import com.comsysto.shop.ui.navigation.EnumProductTypeNavigationItem;
import com.comsysto.shop.ui.page.AbstractBasePage;
import com.comsysto.shop.ui.panel.basket.BasketPanel;
import com.comsysto.shop.ui.panel.product.ProductItemPanel;
import com.comsysto.shop.ui.panel.product.TopSellerRecommendationPanel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


@MountPath("productcatalog/${type}")
@EnumProductTypeNavigationItem(enumClazz = ProductType.class, defaultEnum = "PIZZA", sortOrder = 2)
public class ProductCatalogPage extends AbstractBasePage {

    @SpringBean(name = "productService")
    private ProductService productService;

    private IModel<ProductType> productTypeModel;
    private IModel<List<ProductInfo>> productListModel;
    private Component basketPanel;

    public ProductCatalogPage(PageParameters pageParameters) {
        super(pageParameters);

        productTypeModel = productTypeModel();
        productListModel = productListModel();
        basketPanel = basketPanel();
        add(productView());
        add(basketPanel);
        add(recommendationPanel());
        setOutputMarkupId(true);
    }

    private IModel<ProductType> productTypeModel() {
        return new AbstractReadOnlyModel<ProductType>() {

            private ProductType productType;

            @Override
            public ProductType getObject() {
                if (productType == null) {
                    if (getPageParameters() == null || getPageParameters().get("type") == null) {
                        productType = ProductType.PIZZA;
                    } else {
                        productType = ProductType.fromUrlname(getPageParameters().get("type").toString());
                    }
                }
                return productType;
            }
        };
    }

    @Override
    protected void onConfigure() {
        basketPanel.setVisible(productListModel.getObject().iterator().hasNext());
    }

    private IModel<List<ProductInfo>> productListModel() {
        return new LoadableDetachableModel<List<ProductInfo>>() {
            @Override
            protected List<ProductInfo> load() {
                return productService.findAll(productTypeModel.getObject());
            }
        };
    }

    private Component productView() {
        return new DataView<ProductInfo>("products", productDataProvider()) {

            @Override
            protected void populateItem(final Item<ProductInfo> item) {
                ProductItemPanel pizzaItem = new ProductItemPanel("productItem", item.getModel());
                item.add(pizzaItem.setOutputMarkupId(true));
            }
        };
    }


    private IDataProvider<ProductInfo> productDataProvider() {
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
            public IModel<ProductInfo> model(ProductInfo object) {
                return Model.of(object);
            }

            @Override
            public void detach() {
            }
        };
    }

    private Component basketPanel() {
        return new BasketPanel("basketPanel");
    }

    private TopSellerRecommendationPanel recommendationPanel() {
        return new TopSellerRecommendationPanel("recommendationPanel", productTypeModel);
    }
}
