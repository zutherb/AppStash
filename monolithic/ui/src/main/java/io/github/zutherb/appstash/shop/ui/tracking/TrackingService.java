package io.github.zutherb.appstash.shop.ui.tracking;

import io.github.zutherb.appstash.shop.service.order.model.OrderInfo;

public interface TrackingService {
    void track(OrderInfo order);
}
