package io.github.appstash.memoryleak.classloader;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zutherb
 */
public class ClassLoaderLoggingAgent {

    private static final Object LOCK = new Object();
    private static final AtomicInteger ClASS_LOAD_COUNTER = new AtomicInteger();
    private static final List<ClassLoadingInfo> LOADED_CLASSES = new ArrayList<ClassLoadingInfo>();

    public static void premain(String agentArguments, Instrumentation instrumentation) {
        agentmain(agentArguments, instrumentation);
    }

    public static void agentmain(String agentArguments, Instrumentation instrumentation) {
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                synchronized (LOCK) {
                    LOADED_CLASSES.add(new ClassLoadingInfo(className, loader.getClass().getName(), ClASS_LOAD_COUNTER.getAndIncrement()));
                }
                return classfileBuffer;
            }
        });
    }

    public static List<ClassLoadingInfo> getLastTenLoadedClasses() {
        List<ClassLoadingInfo> result;
        synchronized (LOCK) {
            if (LOADED_CLASSES.size() > 10) {
                int fromIndex = LOADED_CLASSES.size() - 10;
                int toIndex = LOADED_CLASSES.size() - 1;
                result = new ArrayList<ClassLoadingInfo>(LOADED_CLASSES.subList(fromIndex, toIndex));
            } else {
                result = new ArrayList<ClassLoadingInfo>(LOADED_CLASSES);
            }
            return Collections.unmodifiableList(result);
        }
    }
}
