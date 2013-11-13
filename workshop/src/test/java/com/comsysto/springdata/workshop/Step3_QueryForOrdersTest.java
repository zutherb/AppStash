package com.comsysto.springdata.workshop;

import com.comsysto.shop.repository.order.api.OrderRepository;
import com.comsysto.shop.repository.order.model.Order;
import com.comsysto.shop.repository.user.api.UserRepository;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 7/18/13
 * Time: 12:23 PM
 */
@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:com/comsysto/shop/repository/order/spring-context.xml",
                                    "classpath:com/comsysto/shop/ui/spring-profile-context.xml"})
@UsingDataSet(locations="testData.json", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
public class Step3_QueryForOrdersTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private ApplicationContext applicationContext; // required for MongoDBRule to get loaded
    @Rule
    public MongoDbRule mongoDbRule = MongoDbRule.MongoDbRuleBuilder.newMongoDbRule().defaultSpringMongoDb("pizza");


    @Test
    public void testFindAll() {
        List<Order> retrievedOrders = orderRepository.findAll(10, 0, new Sort(Sort.Direction.ASC, "orderId"));
        assertNotNull("Nothing retrieved!", retrievedOrders);
        assertEquals("Unexpected number of retrieved orders!", 5, retrievedOrders.size());
    }

    @Test
    public void testFindAllSorting() {
        int orderCount = (int) orderRepository.countAll();
        List<Order> retrievedOrders = orderRepository.findAll(10, 0, new Sort(Sort.Direction.DESC, "orderDate"));
        assertNotNull("Nothing retrieved!", retrievedOrders);
        assertEquals("Sorting is not implemented correctly!", 55555555L, retrievedOrders.get(0).getOrderId());
        try {
            orderRepository.findAll(10, 0, null);
        }
        catch (NullPointerException e) {
            assertNotNull("Sort=null causes NullPointerException", null);
        }
    }

    @Test
    public void testFindInRange() {
        List<Order> retrievedOrders = orderRepository.findInRange(
                new DateTime(2013,8,8,0,0).toDate(), new DateTime(2013,8,28,0,0).toDate(), 10, 0, new Sort(Sort.Direction.DESC, "orderDate"));
        assertNotNull("Nothing retrieved!", retrievedOrders);
        assertEquals("Retrieved wrong number of orders!", 2, retrievedOrders.size());
        assertEquals("Wrong order retrieved!", 33333333L, retrievedOrders.get(0).getOrderId());
        assertEquals("Wrong order retrieved!", 22222222L, retrievedOrders.get(1).getOrderId());
    }

    @Test
    public void testCountInRange() {
        assertEquals("Counted wrong number of orders!", 2, orderRepository.countInRange(
                new DateTime(2013,8,8,0,0).toDate(), new DateTime(2013,8,28,0,0).toDate()));
    }

    @Test
    public void testFindFirstOrder() {
        Order firstOrder = orderRepository.findFirstOrder();
        assertNotNull("Nothing retrieved", firstOrder);
        assertEquals("Wrong order retrieved!", 11111111L, firstOrder.getOrderId());
    }

    @Test
    public void testFindLastOrder() {
        Order lastOrder = orderRepository.findLastOrder();
        assertNotNull("Nothing retrieved", lastOrder);
        assertEquals("Wrong order retrieved!", 55555555L, lastOrder.getOrderId());
    }

}

