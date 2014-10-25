package io.github.appstash.shop.repository.cart.api;

import io.github.appstash.shop.repository.cart.model.CartItem;

import java.util.List;

public interface CartRepository {
    String create(CartItem cartItem);

    void add(String id, CartItem cartItem);

    void removeFromCart(String cartId, String itemId);

    void clear(String cartId);

    List<CartItem> getCartItems(String cardId);
}
