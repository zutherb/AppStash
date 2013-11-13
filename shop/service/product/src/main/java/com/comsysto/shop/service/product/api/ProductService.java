package com.comsysto.shop.service.product.api;


import com.comsysto.shop.repository.product.model.ProductQuery;
import com.comsysto.shop.repository.product.model.ProductType;
import com.comsysto.common.service.AbstractService;
import com.comsysto.shop.service.product.model.ProductInfo;

import java.util.List;

public interface ProductService extends AbstractService<ProductInfo> {

    List<ProductInfo> findAll(ProductType productType);

    ProductInfo findByUrlname(String urlname);

    List<ProductInfo> findAllSortedByClassifier();

    ProductInfo findByQuery(ProductQuery productQuery);
}
