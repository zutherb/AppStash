package io.github.appstash.shop.service.user.domain

import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 * @author zutherb
 */
@JsonSerialize
class Role implements Serializable {
    private String name;
}
