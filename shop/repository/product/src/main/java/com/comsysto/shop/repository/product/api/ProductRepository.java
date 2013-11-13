package com.comsysto.shop.repository.product.api;


import com.comsysto.common.repository.AbstractRepository;
import com.comsysto.shop.repository.product.model.Product;
import com.comsysto.shop.repository.product.model.ProductQuery;
import com.comsysto.shop.repository.product.model.ProductType;

import java.util.List;


public interface ProductRepository extends AbstractRepository<Product> {

    List<Product> findAll(ProductType type);

    Product findByArticleId( String articleId );

    Product findByUrlname( String urlname );

    Product findByQuery( ProductQuery productQuery);

    List<Product> findAllSortedByClassifier();
}
