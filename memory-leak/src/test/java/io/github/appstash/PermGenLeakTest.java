package io.github.appstash;

import groovy.lang.GroovyShell;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author zutherb
 */
public class PermGenLeakTest {

    private static final GroovyShell GROOVY_SHELL = new GroovyShell(PermGenLeakTest.class.getClassLoader());


    @Test
    @Ignore
    public void testStringInterning() {
        while (true) {
            UUID.randomUUID().toString().intern();
        }
    }

    @Test
    @Ignore
    public void testGroovyClassloading() {
        while (true) {
            GROOVY_SHELL.evaluate("1+5");
        }
    }

    @Test
    @Ignore
    public void testGroovyClassloadingUnloadingSuccess() {
        while (true) {
            GroovyShell groovyShell = new GroovyShell(getClass().getClassLoader());
            groovyShell.evaluate("1+5");
        }
    }

    @Test
    @Ignore
    public void testProxyGeneration() {
        while (true) {
            final Object object = new Object();
            Object proxy = Proxy.newProxyInstance(PermGenLeakTest.class.getClassLoader(), new Class[]{ProxyInterface.class}, new PassThroughInvocationHandler(object));
            proxy.toString();
        }
    }

    @BeforeClass
    public static void setup() {
        ManagementFactory.getMemoryMXBean().setVerbose(true);
        ManagementFactory.getClassLoadingMXBean().setVerbose(true);
    }

    private static interface ProxyInterface { /*NOOP*/
    }

    private static class PassThroughInvocationHandler implements InvocationHandler {

        private Object object;

        public PassThroughInvocationHandler(Object object) {
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(object, args);
        }
    }

}
