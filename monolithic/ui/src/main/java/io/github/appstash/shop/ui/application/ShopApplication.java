package io.github.appstash.shop.ui.application;

import io.github.appstash.shop.ui.application.security.SpringSecurityAuthorizationStrategy;
import io.github.appstash.shop.ui.application.settings.JavaScriptLibrarySettings;
import io.github.appstash.shop.ui.page.home.HomePage;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.settings.IRequestCycleSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

/**
 * @author zutherb
 */
public class ShopApplication extends WebApplication {


    @Override
    protected void init() {
        super.init();
        new AnnotatedMountScanner().scanPackage("io.github.appstash.shop.ui.page").mount(this);
        getMarkupSettings().setStripWicketTags(true);
        getRequestCycleSettings().setRenderStrategy(
                IRequestCycleSettings.RenderStrategy.REDIRECT_TO_RENDER);
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        getSecuritySettings().setAuthorizationStrategy(new SpringSecurityAuthorizationStrategy());
        getDebugSettings().setAjaxDebugModeEnabled(true);

        setJavaScriptLibrarySettings(new JavaScriptLibrarySettings());
        getRequestCycleListeners().add(new MicroserviceRequestCycleListener());
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new ShopSession(request);
    }
}
