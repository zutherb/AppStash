package io.github.appstash.shop.service.product.api;


import io.github.appstash.shop.repository.product.model.ProductQuery;
import io.github.appstash.shop.repository.product.model.ProductType;
import io.github.appstash.common.service.AbstractService;
import io.github.appstash.shop.service.product.model.ProductInfo;

import java.util.List;

public interface ProductService extends AbstractService<ProductInfo> {

    List<ProductInfo> findAll(ProductType productType);

    ProductInfo findByUrlname(String urlname);

    List<ProductInfo> findAllSortedByClassifier();

    ProductInfo findByQuery(ProductQuery productQuery);
}