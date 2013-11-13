package com.comsysto.shop.ui.page.home;

import com.comsysto.shop.service.authentication.api.AuthenticationService;
import com.comsysto.shop.service.product.model.ProductInfo;
import com.comsysto.shop.service.recommendation.api.RecommendationService;
import com.comsysto.shop.ui.navigation.NavigationItem;
import com.comsysto.shop.ui.page.AbstractBasePage;
import com.comsysto.shop.ui.panel.basket.BasketPanel;
import com.comsysto.shop.ui.panel.product.RecommendationItemListPanel;
import org.apache.wicket.Component;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

@NavigationItem(name = "Home", sortOrder = 1)
public class HomePage extends AbstractBasePage {

    @SpringBean(name = "recommendationService")
    private RecommendationService recommendationService;
    @SpringBean(name = "authenticationService")
    private AuthenticationService authenticationService;


    public HomePage() {
        super();

        add(topSellerPanel());
        add(youMayAlsoLikeProductsPanel());
        add(basketPanel());

        setOutputMarkupId(true);
    }

    private Component topSellerPanel() {
        boolean userAuthorized = isUserAuthorized();
        String ressourceKey = userAuthorized ? "your.favorite.products.topic" : "category.top.seller.topic";
        String recommenderType = userAuthorized ? "FAVORITE_PRODUCTS" : "STARTPAGE_TOPSELLER";

        return new RecommendationItemListPanel("topSellerProductsContainer", recommenderType, new ResourceModel(ressourceKey),
                new LoadableDetachableModel<List<ProductInfo>>() {
                    @Override
                    protected List<ProductInfo> load() {
                        return recommendationService.getTopsellerRecommendations(4);
                    }
                }){
        };
    }

    private Component youMayAlsoLikeProductsPanel() {
        return new RecommendationItemListPanel("youMayAlsoLikeProductsContainer", "MAY_ALSO_LIKE", new ResourceModel("you.may.also.like.topic"),
                new LoadableDetachableModel<List<ProductInfo>>() {
                    @Override
                    protected List<ProductInfo> load() {
                        return recommendationService.getCollaborativeFilteringRecommendations(4);
                    }
                }){
        };
    }

    private Component basketPanel() {
        return new BasketPanel("basketPanel");
    }

    private boolean isUserAuthorized() {
        return authenticationService.isAuthorized() && authenticationService.getAuthenticatedUserInfo() != null;
    }
}
