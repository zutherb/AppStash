package io.github.appstash.shop.service.cart.web

import io.github.appstash.shop.service.cart.domain.CartItem
import io.github.appstash.shop.service.cart.domain.CartRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CartController {

    @Autowired
    def CartRepository cartRepository;

    @RequestMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    @ResponseBody
    String create() {
        cartRepository.create()
    }

    @RequestMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    List<CartItem> cart(@PathVariable String id) {
       cartRepository.cart(id)
    }

    @RequestMapping(value = "/add/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    List<CartItem> addToCart(@PathVariable String cartId, @RequestBody CartItem item) {
        cartRepository.add(cartId, item)
    }

    @RequestMapping(value = "/remove/{cartId}/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    @ResponseBody
    List<CartItem> removeFromCart(@PathVariable String cartId, @PathVariable String itemId) {
        cartRepository.remove(cartId, itemId)
    }
}
