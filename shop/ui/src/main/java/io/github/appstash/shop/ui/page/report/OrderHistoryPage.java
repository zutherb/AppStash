package io.github.appstash.shop.ui.page.report;

import io.github.appstash.shop.ui.navigation.NavigationItem;
import io.github.appstash.shop.ui.page.AbstractBasePage;
import io.github.appstash.shop.ui.panel.report.OrderListPanel;
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
