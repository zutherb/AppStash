package io.github.appstash.memoryleak.command;

import io.github.appstash.memoryleak.classloader.ClassLoaderLoggingAgent;
import io.github.appstash.memoryleak.classloader.ClassLoadingInfo;
import io.github.appstash.memoryleak.logging.ConsoleLogger;
import io.github.appstash.memoryleak.logging.SpaceCalculator;

import java.util.List;

import static java.lang.String.format;

/**
 * @author zutherb
 */
public class ClassesPrintCommand implements Command {
    private static final String CLASS_LOADED = "Class loaded";
    private static final ConsoleLogger LOGGER = ConsoleLogger.getConsoleLogger();
    private static final ClassesPrintCommand INSTANCE = new ClassesPrintCommand();

    @Override
    public void execute() {
        List<ClassLoadingInfo> loadedClasses = ClassLoaderLoggingAgent.getLastTenLoadedClasses();
        boolean first = true;
        for (ClassLoadingInfo loadedClass : loadedClasses) {
            if (first) {
                log(CLASS_LOADED, loadedClass);
            } else {
                log("            ", loadedClass);
            }
            first = false;
        }
    }

    private void log(String text, ClassLoadingInfo loadedClass) {
        LOGGER.log(format("%s: %d. %s by %s", SpaceCalculator.appendSpaces(text), loadedClass.getNumber(), loadedClass.getClassName(), loadedClass.getClassLoaderName()));
    }

    public static Command getInstance() {
        return INSTANCE;
    }
}
