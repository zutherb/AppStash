package io.github.zutherb.appstash.shop.ui.navigation;

import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;

import java.lang.annotation.*;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 6/21/13
 * Time: 4:29 PM
 */
@Documented
@Retention( RetentionPolicy.RUNTIME )
@Target( value = ElementType.TYPE )
public @interface EnumProductTypeNavigationItem {

    Class<? extends ProductType> enumClazz();
    String defaultEnum();
    String group() default "main";
    String visible() default "";
    int sortOrder();
}
