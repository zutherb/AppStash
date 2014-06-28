package io.github.appstash.memoryleak.classloader;

/**
 * @author zutherb
 */
public class ClassLoadingInfo {
    private final String className;
    private final String classLoaderName;
    private final int number;

    public ClassLoadingInfo(String className, String classLoaderName, int number) {
        this.className = className;
        this.classLoaderName = classLoaderName;
        this.number = number;
    }

    public String getClassName() {
        return className;
    }

    public String getClassLoaderName() {
        return classLoaderName;
    }

    public int getNumber() {
        return number;
    }
}
