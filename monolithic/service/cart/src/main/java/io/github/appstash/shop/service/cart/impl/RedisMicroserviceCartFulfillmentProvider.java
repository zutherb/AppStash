package io.github.appstash.shop.service.cart.impl;

import io.github.appstash.shop.repository.cart.api.CartRepository;
import io.github.appstash.shop.repository.cart.model.CartItem;
import io.github.appstash.shop.service.cart.api.CartFulfillmentProvider;
import io.github.appstash.shop.service.cart.model.CartItemInfo;
import io.github.appstash.shop.service.product.model.ProductInfo;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zutherb
 */
@Component("redisMicroserviceCart")
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class RedisMicroserviceCartFulfillmentProvider extends AbstractFulfillmentProvider implements CartFulfillmentProvider {

    private Logger logger = LoggerFactory.getLogger(RedisMicroserviceCartFulfillmentProvider.class);
    private CartRepository cartRepository;
    private Mapper mapper;
    private String cartId;

    @Autowired
    public RedisMicroserviceCartFulfillmentProvider(CartRepository cartRepository,
                                                    @Qualifier("dozerMapper") Mapper mapper) {
        this.cartRepository = cartRepository;
        this.mapper = mapper;
    }

    @Override
    public CartItemInfo addItem(ProductInfo productInfo) {
        CartItemInfo cartItemInfo = new CartItemInfo(productInfo);
        getItems().add(cartItemInfo);
        return cartItemInfo;
    }

    @Override
    public boolean removeItem(CartItemInfo item) {
        return getItems().remove(item);
    }

    @Override
    public List<CartItemInfo> getAll() {
        return getItems();
    }

    @Override
    public void clearAll() {
        getItems().clear();
        logger.info("Cart was cleared");
    }

    @Override
    public boolean isEmpty() {
        return getItems().isEmpty();
    }

    @Override
    public List<CartItemInfo> getItems() {
        return cartRepository.getCartItems(cartId)
                .stream()
                .map(cardItem -> mapper.map(cardItem, CartItemInfo.class))
                .collect(Collectors.toList());
    }
}
