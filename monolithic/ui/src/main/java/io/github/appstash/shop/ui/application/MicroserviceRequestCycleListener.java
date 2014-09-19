package io.github.appstash.shop.ui.application;

import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;

public class MicroserviceRequestCycleListener extends AbstractRequestCycleListener {

    public static final String REGISTRATION_SERVER_URL = "registration.microservice.io";

    @Override
    public void onBeginRequest(RequestCycle cycle) {
        if (REGISTRATION_SERVER_URL.equals(cycle.getRequest().getUrl().getHost()) ||
                "10.211.55.103".equals(cycle.getRequest().getClientUrl().getHost()) ||
                "app-server-node-3".equals(cycle.getRequest().getClientUrl().getHost())) {
            ((ShopSession) ShopSession.get()).setMicroServiceMode(true);
        }
    }
}
