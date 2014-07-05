package io.github.appstash.shop.ui.navigation;

/**
 * @author zutherb
 */
public interface NavigationProvider {
    Navigation getNavigation();

    void setClassPathToScan(String classPathToScan);
}
