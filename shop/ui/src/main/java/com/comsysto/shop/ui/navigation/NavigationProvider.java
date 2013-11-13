package com.comsysto.shop.ui.navigation;

/**
 * @author zutherb
 */
public interface NavigationProvider {
    Navigation getNavigation();

    void setClassPathToScan(String classPathToScan);
}
