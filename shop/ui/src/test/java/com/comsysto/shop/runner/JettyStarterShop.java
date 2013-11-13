package com.comsysto.shop.runner;

import org.apache.commons.lang.Validate;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author zutherb
 */
public class JettyStarterShop {

    private static final Logger LOGGER = LoggerFactory .getLogger(JettyStarterShop.class);

    private final Server server;

    public JettyStarterShop(int port){
        Validate.notNull(port, "A Port is needed to start the server");

        server = new Server(port);
        WebAppContext context = new WebAppContext();
        context.setContextPath("/pizza");
        context.setResourceBase("src/main/webapp/");
        context.setDescriptor("src/main/webapp/WEB-INF/web.xml");
        context.setParentLoaderPriority(true);
        server.setHandler(context);
    }

    public void startServer(){
        try {
            LOGGER.info("JETTY SERVER STARTING NOW ...");
            server.start();
            server.join();
        } catch (Exception e) {
            LOGGER.error("Jetty Server could not be started", e);
            System.exit(100);
        }
    }

    public void stopServer(){
        server.destroy();
    }

    public boolean isServerStarted(){
        return server.isStarted();
    }

    public boolean isServerFailed(){
        return server.isFailed();
    }
}
