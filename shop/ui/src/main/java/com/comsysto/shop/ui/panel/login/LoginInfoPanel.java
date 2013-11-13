package com.comsysto.shop.ui.panel.login;

import com.comsysto.shop.ui.event.login.LoginEvent;
import com.comsysto.shop.service.user.model.UserInfo;
import com.comsysto.shop.service.basket.api.Basket;
import com.comsysto.shop.ui.panel.base.AbstractPizzaShopBasePanel;
import com.comsysto.shop.ui.application.ShopApplication;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author zutherb
 */
public class LoginInfoPanel extends AbstractPizzaShopBasePanel {

    private static final long serialVersionUID = 3975460848647745910L;

    @SpringBean
    private Basket basket;

    IModel<Boolean> isAuthorizedModel;
    IModel<UserInfo> userInfoModel;

    public LoginInfoPanel(String id) {
        super(id);
        isAuthorizedModel = isAuthorizedModel();
        userInfoModel = userInfoModel();
        add(usernameLabel());
        add(logoutLink());
        setOutputMarkupId(true);
        setOutputMarkupPlaceholderTag(true);
    }

    private Component logoutLink() {
        return new Link<Void>("logoutLnk") {
            private static final long serialVersionUID = 4400488573342897569L;

            @Override
            public void onClick() {
                getAuthenticationService().clearAuthentication();
                setResponsePage(ShopApplication.get().getHomePage());
                basket.clearAll();
                Session.get().clear();
            }
        };
    }

    private Component usernameLabel() {
        return new Label("username", new PropertyModel<String>(userInfoModel, "username"));
    }

    private IModel<UserInfo> userInfoModel() {
        return new AbstractReadOnlyModel<UserInfo>() {
            private static final long serialVersionUID = -2206159169788096455L;

            @Override
            public UserInfo getObject() {
                return getAuthenticationService().getAuthenticatedUserInfo();
            }
        };
    }

    private IModel<Boolean> isAuthorizedModel() {
        return new AbstractReadOnlyModel<Boolean>() {
            private static final long serialVersionUID = -6807240140221845040L;

            @Override
            public Boolean getObject() {
                return isAuthorized();
            }
        };
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        setVisible(isAuthorizedModel.getObject());
    }

    @Override
    public void detachModels() {
        isAuthorizedModel.detach();
        userInfoModel.detach();
    }


    @Override
    public void onEvent(IEvent<?> event) {
        super.onEvent(event);
        if (event.getPayload() instanceof LoginEvent) {
            setVisible(true);
            ((LoginEvent) event.getPayload()).getTarget().add(this);
        }
    }
}
