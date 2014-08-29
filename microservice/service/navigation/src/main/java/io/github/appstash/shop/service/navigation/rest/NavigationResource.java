package io.github.appstash.shop.service.navigation.rest;

import io.github.appstash.shop.service.navigation.domain.Navigation;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.jongo.JongoCollection;
import restx.security.PermitAll;

import javax.inject.Named;
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


}
