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
 * Time: 13:00 PM
 */
@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:com/comsysto/shop/repository/order/spring-context.xml",
                                    "classpath:com/comsysto/shop/ui/spring-profile-context.xml"})
@UsingDataSet(locations="testData.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
public class Step4_QueryForOrdersPaginationTest extends AbstractJUnit4SpringContextTests {

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
        List<Order> retrievedOrders = orderRepository.findAll(3, 1, new Sort(Sort.Direction.DESC, "orderDate"));
        assertNotNull("Nothing retrieved!", retrievedOrders);
        assertEquals("Unexpected number of retrieved orders!", 3, retrievedOrders.size());
        assertEquals("Wrong order retrieved!", 44444444L, retrievedOrders.get(0).getOrderId());
        assertEquals("Wrong order retrieved!", 33333333L, retrievedOrders.get(1).getOrderId());
        assertEquals("Wrong order retrieved!", 22222222L, retrievedOrders.get(2).getOrderId());
    }

    @Test
    public void testFindInRange() {
        List<Order> retrievedOrders = orderRepository.findInRange(new DateTime(2013,8,1,0,0).toDate(), new DateTime(2013,9,5,0,0).toDate(), 2, 1, new Sort(Sort.Direction.DESC, "orderDate"));
        assertNotNull("Nothing retrieved!", retrievedOrders);
        assertEquals("Unexpected number of retrieved orders!", 2, retrievedOrders.size());
        assertEquals("Wrong order retrieved!", 33333333L, retrievedOrders.get(0).getOrderId());
        assertEquals("Wrong order retrieved!", 22222222L, retrievedOrders.get(1).getOrderId());
    }

    @Test
    public void testFindAllIncompletePage() {
        List<Order> retrievedOrders = orderRepository.findInRange(new DateTime(2013,8,1,0,0).toDate(), new DateTime(2013,9,5,0,0).toDate(), 10, 1, new Sort(Sort.Direction.DESC, "orderDate"));
        assertNotNull("Nothing retrieved!", retrievedOrders);
        assertEquals("Unexpected number of retrieved orders!", 3, retrievedOrders.size());
        assertEquals("Wrong order retrieved!", 33333333L, retrievedOrders.get(0).getOrderId());
        assertEquals("Wrong order retrieved!", 22222222L, retrievedOrders.get(1).getOrderId());
        assertEquals("Wrong order retrieved!", 11111111L, retrievedOrders.get(2).getOrderId());
    }
}

