package io.github.appstash.shop.service.user.api;

import io.github.appstash.common.service.AbstractService;
import io.github.appstash.shop.service.user.model.UserInfo;
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
