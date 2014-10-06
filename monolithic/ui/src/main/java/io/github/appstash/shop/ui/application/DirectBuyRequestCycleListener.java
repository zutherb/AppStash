package io.github.appstash.shop.ui.application;

import io.github.appstash.shop.repository.product.model.ProductQuery;
import io.github.appstash.shop.service.cart.api.Cart;
import io.github.appstash.shop.service.product.api.ProductService;
import io.github.appstash.shop.service.product.model.ProductInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;


public class DirectBuyRequestCycleListener extends AbstractRequestCycleListener {

    @SpringBean
    private Cart cart;

    @SpringBean
    private ProductService productService;

    public DirectBuyRequestCycleListener() {
        super();
        Injector.get().inject(this);
    }

    @Override
    public void onBeginRequest(RequestCycle cycle) {
        StringValue directBuyParameterValue = cycle.getRequest().getRequestParameters().getParameterValue("directBuy");
        if (!directBuyParameterValue.isEmpty()) {
            try {
                ProductInfo productInfo = productService.findByQuery(ProductQuery.create().withUrlname(directBuyParameterValue.toString()));
                if (productInfo != null) {
                    cart.addItem(productInfo);
                }
            } catch (Exception e){
                ShopSession.get().error(String.format("Das Product '%s' konnte nicht gefunden werden."));
            }
        }
    }
}
