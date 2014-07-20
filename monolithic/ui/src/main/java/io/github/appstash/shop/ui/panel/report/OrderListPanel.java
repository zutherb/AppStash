package io.github.appstash.shop.ui.panel.report;

import io.github.appstash.shop.service.order.api.OrderService;
import io.github.appstash.shop.service.order.model.OrderInfo;
import io.github.appstash.shop.ui.panel.base.AbstractPizzaShopBasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilteredPropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;
import org.springframework.data.domain.Sort;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 6/28/13
 * Time: 1:17 PM
 */
public class OrderListPanel extends AbstractPizzaShopBasePanel {

    @SpringBean
    private OrderService orderService;
    private IModel<Date> fromDateModel;
    private IModel<Date> toDateModel;

    public OrderListPanel(String id) {
        super(id);

        fromDateModel = fromDateModel();
        toDateModel = toDateModel();

        add(dateForm());
        add(orderList());
    }

    private LoadableDetachableModel<Date> toDateModel() {
        return new LoadableDetachableModel<Date>() {
            @Override
            protected Date load() {
                return orderService.findLastOrder().getOrderDate();
            }
        };
    }

    private LoadableDetachableModel<Date> fromDateModel() {
        return new LoadableDetachableModel<Date>() {
            @Override
            protected Date load() {
                return orderService.findFirstOrder().getOrderDate();
            }
        };
    }

    private Component dateForm() {
        Form<Void> form = new Form<>("dateForm");
        form.add(createDatePicker("fromDate", fromDateModel));
        form.add(createDatePicker("toDate", toDateModel));
        return form;
    }

    private DateTextField createDatePicker(String id, IModel<Date> model) {
        DateTextField fromDateField = new DateTextField(id, model);
        DatePicker datePicker = new DatePicker();
        datePicker.setShowOnFieldClick(true);
        datePicker.setAutoHide(true);
        fromDateField.add(datePicker);
        return fromDateField;
    }

    private Component orderList() {
        SortableDataProvider<OrderInfo, String> dataProvider = new SortableDataProvider<OrderInfo, String>() {
            @Override
            public Iterator<? extends OrderInfo> iterator(long first, long count) {
                return orderService.findInRange(fromDate(), toDate(), (int) count, (int) first, createSortObject()).iterator();
            }

            private Date fromDate() {
                return new DateTime(fromDateModel.getObject()).withTimeAtStartOfDay().toDate();
            }

            private Date toDate() {
                return new DateTime(toDateModel.getObject()).withTimeAtStartOfDay().plusDays(1).minusSeconds(1).toDate();
            }

            private Sort createSortObject() {
                Sort.Direction sortDirection = Sort.Direction.ASC;
                if (!getSort().isAscending()) {
                    sortDirection = Sort.Direction.DESC;
                }
                return new Sort(sortDirection, getSort().getProperty());
            }

            @Override
            public long size() {
                return orderService.countInRange(fromDate(), toDate());
            }

            @Override
            public IModel<OrderInfo> model(OrderInfo object) {
                return Model.of(object);
            }
        };

        dataProvider.setSort(new SortParam<>("orderDate", false));

        List<IColumn<OrderInfo, String>> columns = new ArrayList<>(4);
        columns.add(new PropertyColumn<>(new Model<>("Order ID"), "orderId", "orderId"));
        columns.add(new PropertyColumn<>(new Model<>("Date"), "orderDate", "orderDate"));
        columns.add(new TextFilteredPropertyColumn<OrderInfo, OrderInfo, String>(new Model<>("Customer"), "user.username"));
        columns.add(new AbstractColumn<OrderInfo, String>(new Model<>("Sum")) {
            @Override
            public void populateItem(Item<ICellPopulator<OrderInfo>> cellItem, String componentId, IModel<OrderInfo> rowModel) {
                cellItem.add(formattedValueLabel(componentId, rowModel.getObject().getTotalSum().doubleValue()));
            }
        });

        return new DefaultDataTable<>("orderList", columns, dataProvider, 10);
    }

    private Label formattedValueLabel(String id, double number) {
        String decimalFormat = ",##0.00";
        DecimalFormat df = new DecimalFormat(decimalFormat);
        String formattedNumber = df.format(number);
        return new Label(id, Model.of("$" + formattedNumber));
    }

}
