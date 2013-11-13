package com.comsysto.shop.service.recommendation.impl;

import com.comsysto.shop.repository.product.model.ProductQuery;
import com.comsysto.shop.service.product.api.ProductService;
import com.comsysto.shop.service.product.model.ProductInfo;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.mongodb.MongoDBDataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 6/28/13
 * Time: 2:51 PM
 */
@Service("mahoutRecommender")
public class MahoutRecommender {

    @Autowired
    private MongoDBDataModel dataModel;
    @Autowired
    private List<Recommender> registeredRecommenders;
    @Autowired
    private ProductService productService;

    public List<ProductInfo> getRecommendations(String userId, int limit) {
        /*
            We're using a random combination of all known recommenders here.
            Therefore, we will collect results for all of them and then shuffle.
         */

        Set<String> resultIds = new HashSet<String>();

        for (Recommender rec : registeredRecommenders) {
            try {
                String dataModelUserId = dataModel.fromIdToLong(userId, true);
                List<RecommendedItem> recommendedItems = rec.recommend(Long.parseLong(dataModelUserId), limit);

                for (RecommendedItem item : recommendedItems) {
                    resultIds.add(dataModel.fromLongToId(item.getItemID()));
                }
            } catch (TasteException e) {
                e.printStackTrace();
            }
        }

        List<ProductInfo> result = new ArrayList<ProductInfo>(resultIds.size());
        for (String articleId : resultIds) {
            result.add(productService.findByQuery(ProductQuery.create().withArticleId(articleId)));
        }

        if (result.size() > limit) {
            Collections.shuffle(result);
            return result.subList(0, limit);
        }
        else {
            return result;
        }
    }

    private void addRecommendedItemToResult(Set<ProductInfo> result, RecommendedItem item) {
        String articleId = dataModel.fromLongToId(item.getItemID());
        result.add(productService.findByQuery(ProductQuery.create().withArticleId(articleId)));
    }
}
