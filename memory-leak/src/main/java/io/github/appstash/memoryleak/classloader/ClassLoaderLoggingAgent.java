package io.github.appstash.memoryleak.classloader;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zutherb
 */
public class ClassLoaderLoggingAgent {

    private static final List<ClassLoadingInfo> LOADED_CLASSES = new ArrayList<ClassLoadingInfo>();

    public static void agentmain(String agentArguments, Instrumentation instrumentation) {
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                LOADED_CLASSES.add(new ClassLoadingInfo(className, loader.getClass().getName()));
                return classfileBuffer;
            }
        });
    }

    public static List<ClassLoadingInfo> getLoadedClasses() {
        return Collections.unmodifiableList(LOADED_CLASSES);
    }
}
