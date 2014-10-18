package io.github.appstash.shop.service.cart.web

import io.github.appstash.shop.service.cart.domain.CartItem
import io.github.appstash.shop.service.cart.domain.CartRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CartController {

    @Autowired
    def CartRepository cartRepository;

    @RequestMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    @ResponseBody
    def UUID create(@RequestBody CartItem item) {
        cartRepository.create(item)
    }

    @RequestMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    def List<CartItem> cart(@PathVariable String id) {
        cartRepository.cart(id)
    }

    @RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    def addToCart(@RequestParam(required = true) String cartId,
                             @RequestBody CartItem item) {
        cartRepository.add(cartId, item)
    }

    @RequestMapping(value = "/removeItemFromCart", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    @ResponseBody
    def removeFromCart(@RequestParam(required = true) String cartId,
                       @RequestParam(required = true) String itemId) {
        cartRepository.removeFromCart(cartId, itemId)
    }

    @RequestMapping(value = "/clear", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    @ResponseBody
    def clearCart(@RequestParam(required = true) String cartId) {
        cartRepository.clear(cartId)
    }
}
