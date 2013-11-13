package com.comsysto.shop.service.order.api;

import com.comsysto.common.service.AbstractService;
import com.comsysto.shop.service.order.model.OrderInfo;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

/**
 * @author zutherb
 */
public interface OrderService extends AbstractService<OrderInfo> {
    OrderInfo submitOrder(OrderInfo orderInfo, String sessionId);
    List<OrderInfo> findAll(int limit, int offset, Sort sort);
    List<OrderInfo> findInRange(Date fromDate, Date toDate, int limit, int offset, Sort sort);
    OrderInfo findFirstOrder();
    OrderInfo findLastOrder();
    long countInRange(Date fromDate, Date toDate);
}
