package com.comsysto.shop.ui.page.checkout;

import com.comsysto.shop.service.order.model.OrderInfo;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;


/**
 * @author zutherb
 */
@MountPath("bestellbestaetigung")
public class OrderConfirmationPage extends CheckoutPage {
    public OrderConfirmationPage(Model<OrderInfo> orderInfoModel) {
        super(orderInfoModel);
    }

    //site must not be validated because it  can only be started with a order model
    protected void validateCheckoutPage() {
    }

    @Override
    protected boolean isReadOnly() {
        return true;
    }

    @Override
    protected boolean showFrequentlyBoughtWithPanel() {
        return false;
    }
}
