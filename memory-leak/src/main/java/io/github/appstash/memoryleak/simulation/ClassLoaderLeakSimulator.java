package io.github.appstash.memoryleak.simulation;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author zutherb
 */
public class ClassLoaderLeakSimulator extends AbstractMemoryLeakSimulator {

    private static final CustomClassLoader LOADER = new CustomClassLoader();

    @Override
    protected void doLeakSimulation() {
        try {

            Class<?> c = LOADER.findClass(MyObject.class.getName());
            Object o = c.newInstance();
            Method m = c.getMethod("toString");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class MyObject {
        @Override
        public String toString() {
            return CustomClassLoader.class.getName();
        }
    }

    private static class CustomClassLoader extends ClassLoader {

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] classData;

            try {
                classData = loadClassData(name);
            } catch (IOException e) {
                throw new ClassNotFoundException("Class [" + name
                        + "] could not be found", e);
            }

            Class<?> c = defineClass(name, classData, 0, classData.length);
            resolveClass(c);
            return c;
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            if ((name != null) && name.startsWith("java.")) {
                return super.loadClass(name);
            }
            return findClass(name);
        }

        private byte[] loadClassData(String name) throws IOException {


            BufferedInputStream in = new BufferedInputStream(
                    ClassLoader.getSystemResourceAsStream(name.replace(".", "/")
                            + ".class"));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int i;

            while ((i = in.read()) != -1) {
                out.write(i);
            }

            in.close();
            byte[] classData = out.toByteArray();
            out.close();

            return classData;
        }


    }
}
