package com.comsysto.dataloader.generator;

import com.comsysto.common.util.SeoUtils;
import com.comsysto.dataloader.reader.AddressCsvReader;
import com.comsysto.dataloader.reader.ProductCsvReader;
import com.comsysto.dataloader.reader.SupplierCsvReader;
import com.comsysto.dataloader.reader.UserCsvReader;
import com.comsysto.shop.repository.user.api.UserRepository;
import com.comsysto.shop.repository.user.model.Address;
import com.comsysto.shop.repository.user.model.Role;
import com.comsysto.shop.repository.user.model.User;
import com.comsysto.shop.repository.order.api.OrderIdProvider;
import com.comsysto.shop.repository.order.api.OrderRepository;
import com.comsysto.shop.repository.order.model.DeliveryAddress;
import com.comsysto.shop.repository.order.model.Order;
import com.comsysto.shop.repository.order.model.OrderItem;
import com.comsysto.shop.repository.order.model.Supplier;
import com.comsysto.shop.repository.product.api.ProductRepository;
import com.comsysto.shop.repository.product.model.Product;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

/**
 * This dataloader loads a basic data set in the database when the application starts and the
 * database is empty
 * <p/>
 * for the test environment the drop.collections can be set to true and the dataloader will
 * always clean the database and so run every time with a fixed set of test data
 *
 * @author zutherb
 */
@Component("dataGenerator")
@ManagedResource(objectName = "com.comsysto.generator:name=DataGenerator")
public class DataGenerator {

    private static Logger logger = LoggerFactory.getLogger(DataGenerator.class);

    private List<Supplier> suppliers;
    private boolean randomPizzaIds = true;
    private int salesPizzaId = 0;

    @Value("${generator.drop.collections}")
    private boolean dropCollections;
    @Value("${generator.generate.orders}")
    private int generateOrders;
    @Value("${mongo.db}")
    private String database;
    @Value("${authentication.salt}")
    private String authenticationSalt;

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;
    @Autowired
    @Qualifier("productCsvReader")
    private ProductCsvReader productCsvReader;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;
    @Autowired
    @Qualifier("userCsvReader")
    private UserCsvReader userCsvReader;
    @Autowired
    @Qualifier("addressCsvReader")
    private AddressCsvReader addressCsvReader;
    @Autowired
    @Qualifier("orderRepository")
    private OrderRepository orderRepository;
    @Autowired
    @Qualifier("supplierCsvReader")
    private SupplierCsvReader supplierCsvReader;
    @Autowired
    @Qualifier("orderIdProvider")
    private OrderIdProvider orderIdProvider;
    @Autowired
    @Qualifier("userPasswordEncoder")
    private ShaPasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("mongoTemplate")
    private MongoTemplate mongoOperations;

    @ManagedOperation
    @PostConstruct
    public void initializeDatabase() throws IOException {
        cleanCollections();
        createSupplierList();

        if(productRepository.findAll().isEmpty()){
            createProducts();
            createUsers();
            createOrders();
        }
    }

    private void createSupplierList() throws IOException {
        suppliers = supplierCsvReader.parseCsv();
    }

    public void cleanCollections() {
        if (dropCollections) {
            mongoOperations.getDb().dropDatabase();
            logger.info("Collections were dropped");
        }
    }


    public void createProducts() throws IOException {
        for (Product product : productCsvReader.parseCsv()) {
            product.setUrlname(SeoUtils.urlFriendly(product.getName()));
            productRepository.save(product);
        }
    }

    public void createUsers() throws IOException {
        Iterator<Address> addresses = addressCsvReader.parseCsv().iterator();
        for (User user : userCsvReader.parseCsv()) {
            user.setAddress(addresses.next());
            user.setUsername(user.getFirstname().toLowerCase() + "." + user.getLastname().toLowerCase());
            user.setPassword(passwordEncoder.encodePassword("customer123", authenticationSalt));
            user.getRoles().add(new Role("customer"));
            userRepository.save(user);
        }
        User user = new User("admin", "Bernd", "Zuther", passwordEncoder.encodePassword("admin123", authenticationSalt),
                new Address("Lindwurmstraße", "80337", "München", "97"), Collections.singleton(new Role("admin")));
        user.setEmail("bernd.zuther@gmail.com");
        userRepository.save(user);
    }

    public void createOrders() throws IOException {
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();
        List<Address> addresses = addressCsvReader.parseCsv();

        for (int i = 0; i < generateOrders; i++) {
            saveOrder(users, products, addresses);
        }
    }

    @ManagedOperation
    public void createOrdersMoreOrders(int orders) throws IOException {
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();
        List<Address> addresses = addressCsvReader.parseCsv();

        for (int i = 0; i < orders; i++) {
            saveOrder(users, products, addresses);
        }
    }

    @Async
    private void saveOrder(List<User> users, List<Product> products, List<Address> addresses) {
        Order order = createOrder(users, addresses, products);
        orderRepository.save(order);
    }

    public Order createOrder(List<User> users, List<Address> addresses, List<Product> products) {
        User randomUser = getRandomUser(users);
        Order order = new Order();
        order.setOrderId(orderIdProvider.nextVal());
        order.setUserId(randomUser.getId());
        order.setDeliveryAddress(getRandomDeliveryAddress(getRandomUser(users), addresses));
        order.setSupplier(suppliers.get(getRandom(0, suppliers.size() - 1)));
        order.setSessionId("dataGenerator");
        addOrderItems(randomUser, order.getOrderItems(), products);
        order.setOrderDate(getRandomDate());
        return order;
    }

    private void addOrderItems(User randomUser,
                               List<OrderItem> orderItems, List<Product> products) {
        int random = getRandom(1, 6);
        for (int i = 0; i < random; i++) {
            OrderItem orderItem = new OrderItem(getRandomPizza(randomUser, products));
            orderItems.add(orderItem);
        }
    }

    private Product getRandomPizza(User randomUser, List<Product> products) {
        int counter = 0;
        if (randomPizzaIds) {
            int timestamp = (int) (new Date().getTime() / 1000);
            int random2 = getRandom(1, 3);
            timestamp /= random2;
            counter = timestamp % products.size();
            Product product = products.get(counter);
            if (randomUser.getCategories().contains(product.getCategory())) {
                return product;
            } else {
                for (Product productWithUserCategory : products) {
                    if (randomUser.getCategories().contains(productWithUserCategory.getCategory())) {
                        return productWithUserCategory;
                    }
                }
            }
        } else {
            counter = salesPizzaId % products.size();
            return products.get(counter);
        }
        return products.get(counter);
    }

    private DeliveryAddress getRandomDeliveryAddress(User user, List<Address> addresses) {
        Address address = addresses.get(getRandom(0, addresses.size() - 1));
        return new DeliveryAddress(user, address);
    }

    public User getRandomUser(List<User> users) {
        return users.get(getRandom(0, users.size() - 1));
    }

    public int getRandom(int minimum, int maximum) {
        Random rn = new Random();
        int n = maximum - minimum + 1;
        int i = Math.abs(rn.nextInt()) % n;
        return minimum + i;
    }

    public Date getRandomDate() {
        try {
            int year = new DateTime().getYear();
            int randomYear = getRandom(year - 2, year);
            int randomMonth = getRandom(1, 12);
            int randomDayOfMonth = getRandom(1, 31);
            DateTime dateTime = new DateTime(randomYear, randomMonth, randomDayOfMonth, getRandom(0, 23), getRandom(0, 59));
            if (dateTime.isBefore(new DateTime().withTimeAtStartOfDay())) {
                return dateTime.toDate();
            } else {
                return getRandomDate();
            }
        } catch (Exception e) {
            return getRandomDate();
        }
    }

    @ManagedAttribute
    public boolean isDropCollections() {
        return dropCollections;
    }

    @ManagedAttribute
    public boolean isRandomPizzaIds() {
        return randomPizzaIds;
    }

    @ManagedAttribute
    public void setRandomPizzaIds(boolean randomPizzaIds) {
        this.randomPizzaIds = randomPizzaIds;
    }

    @ManagedAttribute
    public void setDropCollections(boolean dropCollections) {
        this.dropCollections = dropCollections;
    }

    @ManagedAttribute
    public int getSalesPizzaId() {
        return salesPizzaId;
    }

    @ManagedAttribute
    public void setSalesPizzaId(int salesPizzaId) {
        this.salesPizzaId = salesPizzaId;
    }

    @ManagedAttribute
    public int getGenerateOrders() {
        return generateOrders;
    }

    @ManagedAttribute
    public void setGenerateOrders(int generateOrders) {
        this.generateOrders = generateOrders;
    }
}
