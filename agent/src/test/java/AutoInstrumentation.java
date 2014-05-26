/*
 * Copyright (c) 2012.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import io.github.appstash.AppStashAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * Provides transparent access to the {@link Instrumentation} API, without explicitly starting the application with an agent,
 * attaching one at runtime or even implementing one.
 * <p>
 * <b>Disclaimer:</b> This implementation is a proof of concept using several hacks that are not guaranteed to be consistent
 * across systems and JVMs. Do not rely on it for production applications !
 *
 * @author Julien Nicoulaud <julien.nicoulaud@gmail.com>
 * @see #getInstrumentation() API user entry point
 * @since 1.0.0
 */
public class AutoInstrumentation {

    /**
     * The instance of {@link Instrumentation} retrieved through the agent.
     *
     * @see #getInstrumentation()
     */
    private static Instrumentation instrumentation;

    /**
     * Get an instance of {@link Instrumentation}.
     * <p>
     * Any error occured while retrieving the instance is thrown as {@link RuntimeException}.
     *
     * @return an instance of {@link Instrumentation}
     */
    @SuppressWarnings("unused") // Public API
    synchronized public static Instrumentation getInstrumentation() {
        if (instrumentation == null) {
            try {

                // Generate agent jar
                final File agentJarFile = generateAgentJar();

                // Get current process id
                final int pid = getPid();

                // Load VirtualMachine class
                final Class vmClass = getVirtualMachineClass();

                // Attach to virtual machine
                final Object vm = vmClass.getDeclaredMethod("attach", String.class).invoke(vmClass, String.valueOf(pid));

                // Load agent
                vmClass.getMethod("loadAgent", String.class).invoke(vm, agentJarFile.getPath());

                // Detach
                vmClass.getMethod("detach").invoke(vm);

            } catch (Exception e) {
                throw new RuntimeException("Failed loading instrumentation", e);
            }
        }
        return instrumentation;
    }

    /**
     * Agent entry point.
     * <p>
     * This method is not meant to be used directly, see {@link #getInstrumentation()}.
     *
     * @param args agent arguments, unused
     * @param inst instrument object
     */
    @SuppressWarnings("unused") // Agent entry point
    public static void agentmain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    /**
     * Generate an agent jar file in {@code java.io.tmpdir} with {@code MANIFEST} registering this class as an agent.
     * <p>
     * This method is not meant to be used directly, see {@link #getInstrumentation()}.
     *
     * @return the generated jar file object
     * @throws Exception if the file could not be created or written to, or this class binary stream could not be read
     * @see #getInstrumentation()
     */
    static File generateAgentJar() throws Exception {
        JarOutputStream jarOS = null;
        try {
            // Prepare generated jar output file
            final File jarFile = File.createTempFile(AutoInstrumentation.class.getSimpleName(), ".jar");

            // Prepare manifest
            final Manifest manifest = new Manifest();
            manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
            manifest.getMainAttributes().put(new Attributes.Name("Agent-Class"), AppStashAgent.class.getName());
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

    /**
     * Get current process PID.
     * <p>
     * Tries two methods, the most portable first.
     * <p>
     * This method is not meant to be used directly, see {@link #getInstrumentation()}.
     *
     * @return the current process PID
     * @throws Exception if the PID could not be retrieved
     * @see #getPidFromRuntimeBeanName()
     * @see #getPidFromVMManagement()
     */
    static int getPid() throws Exception {
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

    /**
     * Get current process PID from {@link RuntimeMXBean} name.
     * <p>
     * Uses a trick: {@code java.lang.Runtime} mbean is named {@code PID@HOSTNAME}.
     * <p>
     * This method is not meant to be used directly, see {@link #getInstrumentation()}.
     *
     * @return the current process PID
     * @throws Exception if the PID could not be retrieved
     */
    static int getPidFromRuntimeBeanName() throws Exception {
        try {
            String pid = ManagementFactory.getRuntimeMXBean().getName();
            if (pid.contains("@")) pid = pid.substring(0, pid.indexOf("@"));
            return Integer.parseInt(pid);
        } catch (Throwable t) {
            throw new Exception("Failed getting process PID", t);
        }
    }

    /**
     * Get current process PID from {@code VMManagement} instance.
     * <p>
     * Only tested on HotSpot JVM.
     * <p>
     * This method is not meant to be used directly, see {@link #getInstrumentation()}.
     *
     * @return the current process PID
     * @throws Exception if the PID could not be retrieved
     */
    static int getPidFromVMManagement() throws Exception {
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

    /**
     * Load Java's attach API {@code VirtualMachine} class.
     * <p>
     * Tries current class classloader, then tries after adding {@code tools.jar} to classpath.
     * <p>
     * This method is not meant to be used directly, see {@link #getInstrumentation()}.
     *
     * @return the {@link Class} object
     * @throws Exception if the class could not be loaded
     * @see #getVirtualMachineClassFromClassLoader()
     * @see #addToolsJarToClassPath()
     */
    static Class getVirtualMachineClass() throws Exception {
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

    /**
     * Load Java's attach API {@code VirtualMachine} class from classloader.
     * <p>
     * This method is not meant to be used directly, see {@link #getInstrumentation()}.
     *
     * @return the {@link Class} object
     * @throws ClassNotFoundException if the class could not be found in classloader
     * @see #getVirtualMachineClass()
     */
    static Class getVirtualMachineClassFromClassLoader() throws ClassNotFoundException {
        return AutoInstrumentation.class.getClassLoader().loadClass("com.sun.tools.attach.VirtualMachine");
    }

    /**
     * Locate and add {@code tools.jar} (or {@code classes.jar} on Mac OS) to classpath.
     * <p>
     * This method is not meant to be used directly, see {@link #getInstrumentation()}.
     *
     * @throws Exception if {@code tools.jar} could not be located or added to classpath
     * @see #getVirtualMachineClass()
     */
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
            addURL.invoke(AutoInstrumentation.class.getClassLoader(), url);

        } catch (Throwable t) {
            throw new Exception("Failed adding " + (isMac ? "classes.jar" : "tools.jar") + " to classpath", t);
        }
    }
}
