package io.github.appstash.shop.ui.application;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class ShopSession extends WebSession {

    private boolean microServiceMode;

    public ShopSession(Request request) {
        super(request);
    }

    public boolean isMicroServiceMode() {
        return microServiceMode;
    }

    public void setMicroServiceMode(boolean microServiceMode) {
        this.microServiceMode = microServiceMode;
    }
}
