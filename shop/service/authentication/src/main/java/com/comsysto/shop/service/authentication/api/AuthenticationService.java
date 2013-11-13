package com.comsysto.shop.service.authentication.api;

import com.comsysto.shop.service.authentication.model.LoginInfo;
import com.comsysto.shop.service.user.model.UserInfo;

/**
 * @author zutherb
 */
public interface AuthenticationService {

    boolean authenticate(LoginInfo loginInfo);

    void clearAuthentication();

    boolean isAuthorized(String... permissions);

    UserInfo getAuthenticatedUserInfo();
}
