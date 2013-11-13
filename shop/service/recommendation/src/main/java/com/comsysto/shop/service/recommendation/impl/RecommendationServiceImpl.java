package com.comsysto.shop.service.recommendation.impl;

import com.comsysto.shop.repository.product.model.ProductType;
import com.comsysto.shop.service.authentication.api.AuthenticationService;
import com.comsysto.shop.service.product.api.ProductService;
import com.comsysto.shop.service.product.model.ProductInfo;
import com.comsysto.shop.service.recommendation.api.RecommendationService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Just a dummy implementation.
 *
 * User: christian.kroemer@comsysto.com
 * Date: 6/21/13
 * Time: 3:35 PM
 */
@Service("recommendationService")
public class RecommendationServiceImpl implements RecommendationService {

    private AuthenticationService authenticationService;
    private ProductService productService;
    private MahoutRecommender mahoutRecommender;

    @Autowired
    public RecommendationServiceImpl(@Qualifier("authenticationService") AuthenticationService authenticationService,
                                     @Qualifier("productService") ProductService productService,
                                     @Qualifier("mahoutRecommender") MahoutRecommender mahoutRecommender) {
        this.authenticationService = authenticationService;
        this.productService = productService;
        this.mahoutRecommender = mahoutRecommender;
    }


    @Override
    public List<ProductInfo> getCollaborativeFilteringRecommendations(int limit) {
        if (authenticationService.getAuthenticatedUserInfo() == null) {
            return Collections.emptyList();
        }
        return mahoutRecommender.getRecommendations(authenticationService.getAuthenticatedUserInfo().getId(), limit);
    }

    @Override
    public List<ProductInfo> getFrequentlyBoughtWithProductsRecommendations(int limit) {
        return getRandomlyChosenProducts(limit);
    }

    @Override
    public List<ProductInfo> getRandomRecommendations(int limit) {
        return getRandomlyChosenProducts(limit);
    }

    @Override
    public List<ProductInfo> getTopsellerRecommendations(int limit) {
        // specification is topseller of each category here
        List<ProductInfo> result = new ArrayList<ProductInfo>(ProductType.values().length);
        for (ProductType type : ProductType.values()) {
            if (result.size() >= limit) {
                break;
            }
            CollectionUtils.addIgnoreNull(result, getRandomlyChosenProducts(1, type).get(0));
        }
        return result;
    }

    @Override
    public List<ProductInfo> getTopsellerRecommendations(ProductType type, int limit) {
        return getRandomlyChosenProducts(limit, type);
    }

    @Override
    public List<ProductInfo> getViewedByOthersRecommendations(String articleId, int limit) {
        return getRandomlyChosenProducts(limit);
    }


    private List<ProductInfo> getRandomlyChosenProducts(int count) {
        List<ProductInfo> allProducts = productService.findAll();
        Collections.shuffle(allProducts);
        return allProducts.subList(0, count);
    }

    private List<ProductInfo> getRandomlyChosenProducts(int count, ProductType type) {
        List<ProductInfo> allProducts = productService.findAll(type);
        Collections.shuffle(allProducts);
        return allProducts.subList(0, count);
    }
}
