package io.github.appstash.shop.service.cart.api;

public interface RedisMicroserviceCartFulfillmentProvider extends CartFulfillmentProvider {
    void setCartId(String cartId);
}
