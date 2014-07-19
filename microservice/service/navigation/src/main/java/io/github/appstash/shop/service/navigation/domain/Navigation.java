package io.github.appstash.shop.service.navigation.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.Id;

/**
 * @author zutherb
 */
public class Navigation {
    @Id
    private String name;
    private long sum;

    public Navigation() {
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public long getSum() {
        return sum;
    }
}
