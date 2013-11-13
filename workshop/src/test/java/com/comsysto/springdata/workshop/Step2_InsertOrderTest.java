package com.comsysto.springdata.workshop;

import com.comsysto.shop.repository.order.api.OrderRepository;
import com.comsysto.shop.repository.order.model.DeliveryAddress;
import com.comsysto.shop.repository.order.model.Order;
import com.comsysto.shop.repository.order.model.OrderItem;
import com.comsysto.shop.repository.product.model.Product;
import com.comsysto.shop.repository.product.model.ProductType;
import com.comsysto.shop.repository.user.api.UserRepository;
import com.comsysto.shop.repository.user.model.Address;
import com.comsysto.shop.repository.user.model.User;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 7/11/13
 * Time: 3:50 PM
 */
@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:com/comsysto/shop/repository/order/spring-context.xml",
                                    "classpath:com/comsysto/shop/ui/spring-profile-context.xml"})
public class Step2_InsertOrderTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void testOrderDocumentAnnotation() {
        Document documentAnnotation = Order.class.getAnnotation(Document.class);
        assertNotNull("Not annotated as document!", documentAnnotation);
        assertEquals("Wrong document name!", "order", documentAnnotation.collection());
    }

    @Test(expected = RuntimeException.class)
    public void testRejectOrderWithInvalidUser() {
        User nonPersistentUser = createUser();
        Order order = new Order(123456789L, nonPersistentUser.getId(), createOrderItems(), new DeliveryAddress(), "dummy");
        orderRepository.save(order);
    }

    @Test
    public void testInsertOrder() {
        User nonPersistentUser = createUser();
        userRepository.save(nonPersistentUser);
        User persistentUser = userRepository.findByUsername(nonPersistentUser.getUsername());
        Order order = new Order(123456789L, persistentUser.getId(), createOrderItems(), new DeliveryAddress(), "dummy");

        long orderCountBefore = orderRepository.countAll();
        orderRepository.save(order);
        assertEquals("Unexpected order count!", orderCountBefore + 1, orderRepository.countAll());
    }

    private User createUser() {
        User user = new User("username", "First", "Last", "password", new Address(), null);
        user.setEmail("username@user.com");
        return user;
    }

    private List<OrderItem> createOrderItems() {
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        orderItems.add(new OrderItem(new Product("testId", ProductType.PIZZA, "Test Pizza", 9.99)));
        return orderItems;
    }

    @After
    public void cleanUp() {
        userRepository.delete(userRepository.findByUsername("username"));
        orderRepository.delete(orderRepository.findByOrderId(123456789L));
    }
}
