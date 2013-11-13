package com.comsysto.shop.service.user.impl;

import com.comsysto.shop.repository.user.model.User;
import com.comsysto.shop.service.user.api.UserService;
import com.comsysto.shop.service.user.model.AddressInfo;
import com.comsysto.shop.service.user.model.RoleInfo;
import com.comsysto.shop.service.user.model.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.Collections;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * @author zutherb
 */
@ActiveProfiles("test")
@ContextConfiguration(locations = "classpath:com/comsysto/shop/service/user/spring-context.xml")
public class UserServiceImplTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private UserService userService;

    @Autowired
    private MongoOperations mongoOperations;

    @Test
    public void testFindByUsername() {
        mongoOperations.dropCollection(User.class);
        userService.save(new UserInfo("found", "securepw", Collections.<RoleInfo>emptySet(), new AddressInfo("", "", "", "", "", "", "", "", 0, 0)));
        UserInfo userInfo = userService.findByUsername("found");
        assertNotNull(userInfo);
        UserInfo notFound = userService.findByUsername("not-found");
        assertNull(notFound);
    }

}
