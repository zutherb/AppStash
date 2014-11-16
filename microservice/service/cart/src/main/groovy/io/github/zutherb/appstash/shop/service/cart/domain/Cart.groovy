package io.github.zutherb.appstash.shop.service.cart.domain

class Cart implements Serializable {
    def String uuid;
    def List<CartItem> cartItems;

    Cart(String uuid, List<CartItem> cartItems) {
        this.uuid = uuid
        this.cartItems = cartItems
    }
}
