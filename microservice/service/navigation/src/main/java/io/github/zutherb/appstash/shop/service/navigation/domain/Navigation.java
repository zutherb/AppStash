package io.github.zutherb.appstash.shop.service.navigation.domain;

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
        return Character.toString(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getUrlname() {
        return name.toLowerCase();
    }

    public long getSum() {
        return sum;
    }
}
