package com.comsysto.shop.ui.panel;

import com.comsysto.shop.service.order.model.OrderInfo;
import com.comsysto.shop.ui.panel.base.AbstractPizzaShopBasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 * @author zutherb
 */
public class DeliveryAdressEditPanel extends AbstractPizzaShopBasePanel {
    private static final long serialVersionUID = -7031284847456677558L;

    private IModel<OrderInfo> orderModel;

    public DeliveryAdressEditPanel(String id, IModel<OrderInfo> orderModel) {
        super(id);
        this.orderModel = orderModel;
        add(deliveryAdressEditForm());
    }

    private Component deliveryAdressEditForm() {
        Form<OrderInfo> deliveryAdressEditForm = new Form<OrderInfo>("deliveryAdressEditForm");
        deliveryAdressEditForm.add(new TextField<String>("firstname", new PropertyModel<String>(orderModel, "deliveryAddress.firstname")).setRequired(true));
        deliveryAdressEditForm.add(new TextField<String>("lastname", new PropertyModel<String>(orderModel, "deliveryAddress.lastname")).setRequired(true));
        deliveryAdressEditForm.add(new TextField<String>("street", new PropertyModel<String>(orderModel, "deliveryAddress.street")).setRequired(true));
        deliveryAdressEditForm.add(new TextField<String>("zip", new PropertyModel<String>(orderModel, "deliveryAddress.zip")).setRequired(true));
        deliveryAdressEditForm.add(new TextField<String>("city", new PropertyModel<String>(orderModel, "deliveryAddress.city")).setRequired(true));
        deliveryAdressEditForm.add(new SubmitLink("deliveryAdressEditFormSubmit") {
            private static final long serialVersionUID = 8821619700889289116L;

            @Override
            public void onSubmit() {
                deliveryAdressEditFormSubmit();
            }
        });
        return deliveryAdressEditForm;
    }

    protected void deliveryAdressEditFormSubmit() {

    }

    @Override
    protected void onBeforeRender() {
        setVisible(isAuthorized());
        super.onBeforeRender();
    }

}
