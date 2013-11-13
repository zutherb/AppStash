package com.comsysto.shop.ui.page.report;

import com.comsysto.shop.ui.navigation.NavigationItem;
import com.comsysto.shop.ui.page.AbstractBasePage;
import com.comsysto.shop.ui.panel.report.OrderListPanel;
import org.apache.wicket.Component;
import org.springframework.security.access.annotation.Secured;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 6/28/13
 * Time: 1:13 PM
 */
@MountPath("orderhistory")
@NavigationItem(name = "Order History", sortOrder = 1, group = "Reports")
@Secured("admin")
public class OrderHistoryPage extends AbstractBasePage {

    public OrderHistoryPage() {
        super();
        add(orderListPanel());
    }

    private Component orderListPanel() {
        return new OrderListPanel("orderListPanel");
    }
}
