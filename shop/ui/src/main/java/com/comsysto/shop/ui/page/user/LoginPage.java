package com.comsysto.shop.ui.page.user;

import com.comsysto.shop.ui.navigation.ModalPanelItem;
import com.comsysto.shop.ui.navigation.NavigationItem;
import com.comsysto.shop.ui.page.AbstractBasePage;
import com.comsysto.shop.ui.panel.login.LoginModalPanel;
import com.comsysto.shop.ui.panel.login.LoginPanel;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author zuther
 */
@MountPath("login")
@NavigationItem(name = "Sign In", sortOrder = 1, group = "Sign In", visible = "!@authenticationService.isAuthorized()")
@ModalPanelItem(modalPanel = LoginModalPanel.class)
public class LoginPage extends AbstractBasePage {
    
    public LoginPage(){
        super();
        feedback.setVisibilityAllowed(false);
        add(new LoginPanel("loginPanel"));
    }

}
