package com.comsysto.shop.service.order.model;

import com.comsysto.shop.service.user.model.UserInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author zutherb
 */
public class OrderInfo implements Serializable {

    private Long orderId;
    private UserInfo user;
    private List<OrderItemInfo> orderItems;
    private DeliveryAddressInfo deliveryAddress;
    private Date orderDate;
    private String sessionId;

    private OrderInfo() {
    }

    public OrderInfo(UserInfo user, DeliveryAddressInfo deliveryAddress, List<OrderItemInfo> orderItems, String sessionId) {
        this.user = user;
        this.orderItems = orderItems;
        this.deliveryAddress = deliveryAddress;
        this.sessionId = sessionId;
    }

    public OrderInfo(Long orderId, String sessionId, OrderInfo orderInfo) {
        this.orderId = orderId;
        this.sessionId = sessionId;
        this.user = orderInfo.getUser();
        this.orderItems = orderInfo.getOrderItems();
        this.deliveryAddress = orderInfo.getDeliveryAddress();
    }

    public OrderInfo(Long orderId, OrderInfo orderInfo, List<OrderItemInfo> orderItemInfos, String sessionId) {
        this(orderId, sessionId, orderInfo);
        this.orderItems = orderItemInfos;
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public List<OrderItemInfo> getOrderItems() {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }
        return Collections.unmodifiableList(orderItems);
    }

    public DeliveryAddressInfo getDeliveryAddress() {
        return deliveryAddress;
    }

    public Double getTotalSum() {
        Double totalSum = 0.0;
        for (OrderItemInfo orderItemInfo : orderItems) {
            totalSum += orderItemInfo.getTotalSum();
        }
        return totalSum;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }
}
