package com.comsysto.shop.service.user.api;

import com.comsysto.common.service.AbstractService;
import com.comsysto.shop.service.user.model.UserInfo;
import org.bson.types.ObjectId;

/**
 * @author zutherb
 */
public interface UserService extends AbstractService<UserInfo> {
    UserInfo findById(ObjectId userId);
    UserInfo findByUsername(String username);
    boolean existsUserWithEmail(String email);
    void save(UserInfo userInfo);
}
