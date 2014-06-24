package io.github.appstash.memoryleak.classloader;

/**
 * @author zutherb
 */
public class ClassLoadingInfo {
    private final String className;
    private final String classLoaderName;

    public ClassLoadingInfo(String className, String classLoaderName) {
        this.className = className;
        this.classLoaderName = classLoaderName;
    }

    public String getClassName() {
        return className;
    }

    public String getClassLoaderName() {
        return classLoaderName;
    }
}
