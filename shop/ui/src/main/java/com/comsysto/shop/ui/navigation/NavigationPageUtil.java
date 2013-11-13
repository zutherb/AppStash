package com.comsysto.shop.ui.navigation;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.SetUtils;
import org.apache.wicket.Page;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class NavigationPageUtil {

    private static final Predicate PAGE_PREDICATE = new Predicate() {
        @Override
        public boolean evaluate(Object pageCandidate) {
            return Page.class.isAssignableFrom((Class<?>) pageCandidate);
        }
    };

    @SuppressWarnings("unchecked") // apache commons collection api does not support generics
    public static Set<Class<? extends Page>> getAnnotatedWicketPage(String packageScanPath, Class<? extends Annotation> annotationClazz) {
        Reflections reflections = new Reflections(packageScanPath);
        return SetUtils.predicatedSet(reflections.getTypesAnnotatedWith(annotationClazz), PAGE_PREDICATE);
    }

}
