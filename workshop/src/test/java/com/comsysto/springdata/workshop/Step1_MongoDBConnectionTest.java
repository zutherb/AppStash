package com.comsysto.springdata.workshop;

import com.mongodb.Mongo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static junit.framework.Assert.*;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 7/11/13
 * Time: 2:42 PM
 */
@ActiveProfiles("default")
@ContextConfiguration(locations = { "classpath:com/comsysto/common/repository/spring-mongo-context.xml",
                                    "classpath:com/comsysto/shop/ui/spring-profile-context.xml"})
public class Step1_MongoDBConnectionTest extends AbstractJUnit4SpringContextTests {

    @Value("${mongo.db}")
    private String databaseName;

    @Test
    public void testRequiredBeansExist() {
        Object mongoBean = applicationContext.getBean("mongo");
        assertTrue("mongo bean has wrong type!", mongoBean instanceof Mongo);

        Object mongoTemplateBean = applicationContext.getBean("mongoTemplate");
        assertTrue("mongoTemplate bean has wrong type!", mongoTemplateBean instanceof MongoTemplate);

    }

    @Test
    public void testDatabaseConnection() {
        MongoTemplate mongoTemplate = (MongoTemplate) applicationContext.getBean("mongoTemplate");
        assertEquals(databaseName, mongoTemplate.getDb().getName());
    }

}
