package com.comsysto.shop.repository.user.api;

import com.comsysto.common.repository.AbstractRepository;
import com.comsysto.shop.repository.user.model.User;
import org.bson.types.ObjectId;

/**
 * @author zutherb
 */
public interface UserRepository extends AbstractRepository<User> {
    User findById(ObjectId userId);
    User findByUsername(String username);
    boolean existsUserWithEmail(String email);
}
