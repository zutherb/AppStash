package io.github.appstash.shop.service.navigation;

import restx.config.ConfigLoader;
import restx.config.ConfigSupplier;
import restx.factory.Provides;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;
import restx.mongo.MongoModule;
import restx.security.*;
import restx.factory.Module;
import restx.factory.Provides;
import javax.inject.Named;

import java.nio.file.Paths;

@Module
public class AppModule {
    @Provides
    public SignatureKey signatureKey() {
         return new SignatureKey("f0a34ee4-866e-4d4b-98b5-5a2181cfb237 -6242533119131308498 navigation-microservice navigation-microservice".getBytes(Charsets.UTF_8));
    }

    @Provides
    @Named("restx.admin.password")
    public String restxAdminPassword() {
        return "admin123";
    }

    @Provides @Named(MongoModule.MONGO_DB_NAME)
    public String dbName() {
        return System.getProperty("mongo.db", "shop");
    }

    @Provides
    public ConfigSupplier appConfigSupplier(ConfigLoader configLoader) {
        // Load settings.properties in io.github.appstash.shop.service.navigation package as a set of config entries
        return configLoader.fromResource("io/github/appstash/shop/service/navigation/settings");
    }
}
