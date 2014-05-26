package io.github.appstash;

import io.github.appstash.task.ClassLoadingLoggingTask;
import io.github.appstash.task.MemoryLoggingTask;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zutherb
 */
public class AppStashAgent {


    public static void agentmain(String agentArguments, Instrumentation instrumentation) {
        System.out.append("Test");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
        scheduler.scheduleAtFixedRate(new MemoryLoggingTask(), 0, 1, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(new ClassLoadingLoggingTask(), 0, 1, TimeUnit.SECONDS);
//      scheduler.execute();
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                byte[] result = new byte[0];

                try {
                    String dotClassName = className.replace('/', '.');

                    ClassPool cp = ClassPool.getDefault();
                    CtClass ctClazz = cp.get(dotClassName);

                    for (CtMethod method1 : ctClazz.getDeclaredMethods()) {
                        method1.addLocalVariable("elapsedTime", CtClass.longType);

                        method1.insertBefore("elapsedTime = System.nanoTime();");
                        method1.insertAfter(" { elapsedTime = System.nanoTime() - elapsedTime; "
                                + "System.out.println(\" Method elapsedTime = \" + elapsedTime);}");
                        result = ctClazz.toBytecode();

                    }

                } catch (Throwable e) {
                    e.printStackTrace();
                }
                return result;
            }
        });
    }
}
