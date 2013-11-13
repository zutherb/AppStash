package com.comsysto.shop.ui.event.login;

import com.comsysto.shop.ui.event.AjaxEvent;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author zutherb
 */
public class LoginEvent implements AjaxEvent {

    private AjaxRequestTarget target;

    public LoginEvent(Component component, AjaxRequestTarget target) {
        this.target = target;
    }

    @Override
    public AjaxRequestTarget getTarget() {
        return target;
    }
}
