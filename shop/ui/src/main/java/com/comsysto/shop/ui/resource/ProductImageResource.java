package com.comsysto.shop.ui.resource;


import com.comsysto.shop.service.product.model.ProductInfo;
import org.apache.commons.io.FileUtils;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.model.IModel;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;


public class ProductImageResource extends BufferedDynamicImageResource {
    private IModel<ProductInfo> productInfoModel;

    public ProductImageResource(IModel<ProductInfo> productInfoModel) {
        super();
        this.productInfoModel = productInfoModel;
    }

    @Override
    protected byte[] getImageData(Attributes attributes) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(getFullProductImagePath());
            return FileUtils.readFileToByteArray(classPathResource.getFile());
        } catch (IOException e) {
            throw new IllegalStateException("file not found", e);
        }
    }

    public String getFullProductImagePath() {
        if (productInfoModel == null) return null;
        return "assets/img/Pizza/" + productInfoModel.getObject().getUrlname() + ".jpg";
    }
}
