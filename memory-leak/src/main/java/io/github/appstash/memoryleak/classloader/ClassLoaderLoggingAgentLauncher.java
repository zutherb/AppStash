package io.github.appstash.memoryleak.classloader;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * @author zutherb
 */
public class ClassLoaderLoggingAgentLauncher {

    public static synchronized void launch(String[] args) {
        try {
            final File agentJarFile = generateAgentJar();
            final int pid = (Arrays.asList(args).isEmpty()) ? getPid() : Integer.valueOf(args[0]);
            final Class vmClass = getVirtualMachineClass();

            final Object vm = vmClass.getDeclaredMethod("attach", String.class).invoke(vmClass, String.valueOf(pid));

            //vmClass.getMethod("loadAgentLibrary", String.class).invoke(System.getProperty("java.class.path"));
            vmClass.getMethod("loadAgent", String.class).invoke(vm, agentJarFile.getPath());

            vmClass.getMethod("detach").invoke(vm);

        } catch (Exception e) {
            throw new RuntimeException("Failed loading instrumentation", e);
        }
    }

    private static File generateAgentJar() throws Exception {
        JarOutputStream jarOS = null;
        try {
            // Prepare generated jar output file
            final File jarFile = File.createTempFile(ClassLoaderLoggingAgentLauncher.class.getSimpleName(), ".jar");

            // Prepare manifest
            final Manifest manifest = new Manifest();
            manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
            manifest.getMainAttributes().put(new Attributes.Name("Agent-Class"), ClassLoaderLoggingAgent.class.getName());
            manifest.getMainAttributes().put(new Attributes.Name("Can-Redefine-Classes"), Boolean.TRUE.toString());
            manifest.getMainAttributes().put(new Attributes.Name("Can-Retransform-Classes"), Boolean.TRUE.toString());
            manifest.getMainAttributes().put(new Attributes.Name("Can-Set-Native-Method-Prefix"), Boolean.TRUE.toString());

            // Prepare jar output stream
            jarOS = new JarOutputStream(new FileOutputStream(jarFile), manifest);

            // Return generated jar file
            return jarFile;

        } catch (Throwable t) {
            throw new Exception("Failed generating agent jar", t);
        } finally {
            if (jarOS != null) jarOS.close();
        }
    }

    private static int getPid() throws Exception {
        try {
            try {
                return getPidFromRuntimeBeanName();
            } catch (Throwable t) {
                return getPidFromVMManagement();
            }
        } catch (Throwable t) {
            throw new Exception("Failed getting process PID", t);
        }
    }

    private static int getPidFromRuntimeBeanName() throws Exception {
        try {
            String pid = ManagementFactory.getRuntimeMXBean().getName();
            if (pid.contains("@")) pid = pid.substring(0, pid.indexOf("@"));
            return Integer.parseInt(pid);
        } catch (Throwable t) {
            throw new Exception("Failed getting process PID", t);
        }
    }

    private static int getPidFromVMManagement() throws Exception {
        try {
            final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
            final Field jvmField = runtimeMXBean.getClass().getDeclaredField("jvm");
            jvmField.setAccessible(true);
            Object vmManagement = jvmField.get(runtimeMXBean);
            final Method getProcessIdMethod = vmManagement.getClass().getDeclaredMethod("getProcessId");
            getProcessIdMethod.setAccessible(true);
            return (Integer) getProcessIdMethod.invoke(vmManagement);
        } catch (Throwable t) {
            throw new Exception("Failed getting process PID from VManagement", t);
        }
    }

    private static Class getVirtualMachineClass() throws Exception {
        try {
            try {
                return getVirtualMachineClassFromClassLoader();
            } catch (ClassNotFoundException e) {
                addToolsJarToClassPath();
                return getVirtualMachineClassFromClassLoader();
            }
        } catch (Throwable t) {
            throw new Exception("Failed loading attach API class", t);
        }
    }

    private static Class getVirtualMachineClassFromClassLoader() throws ClassNotFoundException {
        return ClassLoaderLoggingAgentLauncher.class.getClassLoader().loadClass("com.sun.tools.attach.VirtualMachine");
    }

    static void addToolsJarToClassPath() throws Exception {
        final boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        try {
            // Locate tools.jar
            URL url = null;
            MalformedURLException mfe = null;
            try {
                url = new URL(new File(System.getProperty("java.home")).toURI().toString() + "/../" + (isMac ? "Classes/classes.jar" : "lib/tools.jar"));
            } catch (MalformedURLException e) {
                mfe = e;
            }
            if (mfe != null || url == null || !new File(url.toURI()).isFile())
                throw new Exception("Failed locating " + (isMac ? "classes.jar" : "tools.jar") + ", make sure you run this program on a JDK or explicitly add it to classpath", mfe);

            // Add it to classpath
            final Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURL.setAccessible(true);
            addURL.invoke(ClassLoaderLoggingAgentLauncher.class.getClassLoader(), url);

        } catch (Throwable t) {
            throw new Exception("Failed adding " + (isMac ? "classes.jar" : "tools.jar") + " to classpath", t);
        }
    }
}
