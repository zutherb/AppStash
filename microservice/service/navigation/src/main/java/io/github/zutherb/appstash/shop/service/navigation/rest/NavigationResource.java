package io.github.zutherb.appstash.shop.service.navigation.rest;

import io.github.zutherb.appstash.shop.service.navigation.domain.Navigation;
import io.github.zutherb.appstash.shop.service.navigation.domain.Navigation;
import org.apache.commons.io.FileUtils;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.jongo.JongoCollection;
import restx.security.PermitAll;

import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
@RestxResource
@PermitAll
public class NavigationResource {

    private JongoCollection product;

    public NavigationResource(@Named("product") JongoCollection product) {
        this.product = product;
    }

    @GET("/all")
    public List<Navigation> navigation() {
        return product.get()
                .aggregate("{$group: {_id : \"$type\", sum : {$sum : 1}}}")
                .as(Navigation.class);
    }

    @GET("/manifest")
    public String version() throws IOException {
        URL manifestFileUrl = getClass().getClassLoader().getResource("META-INF/MANIFEST.MF");
        File manifestFile = new File(manifestFileUrl.getFile());
        String fileContentAsString = FileUtils.readFileToString(manifestFile);
        return fileContentAsString;
    }

    @GET("/shutdown")
    public void shutdown() throws IOException {
        System.exit(0);
    }

}
