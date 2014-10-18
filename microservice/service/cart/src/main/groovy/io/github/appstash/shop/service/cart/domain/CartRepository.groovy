package io.github.appstash.shop.service.cart.domain

import org.springframework.stereotype.Component

@Component
class CartRepository {

    Map<String, List<CartItem>> cards = new HashMap<>();

    List<CartItem> cart(String id){
        cards.get(id)
    }

    String create() {
        def uuid = UUID.randomUUID().toString()
        cards.put(uuid, new ArrayList<CartItem>())
        uuid
    }

    def add(String id, CartItem cartItem) {
        cards.get(id).add(cartItem);
    }

    def remove(String cartId, String itemId) {
        def cartItems = cards.get(cartId)
        cartItems.forEach({
            cardItem ->
            if(cardItem.uuid == itemId) cartItems.remove(cardItem)
        });
    }
}
