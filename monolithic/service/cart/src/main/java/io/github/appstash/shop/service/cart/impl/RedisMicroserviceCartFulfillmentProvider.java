package io.github.appstash.shop.service.cart.impl;

import io.github.appstash.shop.repository.cart.api.CartRepository;
import io.github.appstash.shop.repository.cart.model.CartItem;
import io.github.appstash.shop.service.cart.api.CartFulfillmentProvider;
import io.github.appstash.shop.service.cart.model.CartItemInfo;
import io.github.appstash.shop.service.product.model.ProductInfo;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zutherb
 */
@Component("redisMicroserviceCart")
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class RedisMicroserviceCartFulfillmentProvider extends AbstractFulfillmentProvider implements CartFulfillmentProvider {

    private final Object lock = new Object();
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
        synchronized (lock) {
            CartItemInfo cartItemInfo = createCartItemInfo(productInfo);
            CartItem map = mapToCartItem(cartItemInfo);
            if (StringUtils.isEmpty(cartId)) {
                cartId = StringUtils.strip(cartRepository.create(map), "\"");
            } else {
                cartRepository.add(cartId, map);
            }
            return cartItemInfo;
        }
    }

    private CartItemInfo createCartItemInfo(ProductInfo productInfo) {
        return new CartItemInfo(productInfo);
    }

    private CartItem mapToCartItem(CartItemInfo cartItemInfo) {
        return mapper.map(cartItemInfo, CartItem.class);
    }

    @Override
    public void removeItem(CartItemInfo item) {
        synchronized (lock) {
            cartRepository.removeFromCart(cartId, item.getUuid());
        }
    }

    @Override
    public List<CartItemInfo> getAll() {
        synchronized (lock) {
            return getItems();
        }
    }

    @Override
    public void clearAll() {
        synchronized (lock) {
            cartRepository.clear(cartId);
            logger.info("Cart was cleared");
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (lock) {
            return getItems().isEmpty();
        }
    }

    @Override
    public List<CartItemInfo> getItems() {
        if (StringUtils.isNotEmpty(cartId)) {
            return cartRepository.getCartItems(cartId)
                    .stream()
                    .map(cardItem -> mapper.map(cardItem, CartItemInfo.class))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
