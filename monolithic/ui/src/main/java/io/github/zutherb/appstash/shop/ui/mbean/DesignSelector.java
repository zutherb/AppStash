package io.github.zutherb.appstash.shop.ui.mbean;

import org.springframework.jmx.export.annotation.ManagedOperation;

import java.util.List;

/**
 * Created by berndzuther on 11.04.15.
 */
public interface DesignSelector {
    String getDesignType();
    void setDesignType(String name);
    List<String> getAvailableDesignTypes();
    void standard();
    void next();
}
