package io.github.zutherb.appstash.shop.service.navigation;

import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.server.WebServer;
import restx.server.JettyWebServer;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * This class can be used to run the app.
 * <p>
 * Alternatively, you can deploy the app as a war in a regular container like tomcat or jetty.
 * <p>
 * Reading the port from system env PORT makes it compatible with heroku.
 */
public class AppServer {
    private static final String MONGODB_PORT_27017_TCP_ADDR = "MONGODB_PORT_27017_TCP_ADDR";

    private static final Logger LOGGER = LoggerFactory.getLogger("AppServer");

    private static final String WEB_INF_LOCATION = "src/main/webapp/WEB-INF/web.xml";
    private static final String WEB_APP_LOCATION = "src/main/webapp";

    public static void main(String[] args) throws Exception {
        String mongodbHost = System.getenv(MONGODB_PORT_27017_TCP_ADDR);
        if (isNotEmpty(mongodbHost)) {
            String mongoUri = "mongodb://" + mongodbHost;
            System.setProperty("mongo.uri", mongoUri);
            LOGGER.info("Rewrite mongo.uri to " + mongoUri);
        }

        int port = Integer.valueOf(Optional.fromNullable(System.getenv("PORT")).or("18090"));
        WebServer server = new JettyWebServer(
                System.getProperty("restx.web-inf.location", WEB_INF_LOCATION),
                System.getProperty("restx.web-app.location", WEB_APP_LOCATION),
                port,
                "0.0.0.0");

        /*
         * load mode from system property if defined, or default to dev
         * be careful with that setting, if you use this class to launch your server in production, make sure to launch
         * it with -Drestx.mode=prod or change the default here
         */
        System.setProperty("restx.mode", System.getProperty("restx.mode", "dev"));
        System.setProperty("restx.app.package", "io.github.zutherb.appstash.shop.service.navigation");

        server.startAndAwait();
    }
}
