package io.github.appstash.memoryleak.command;

import io.github.appstash.memoryleak.logging.ConsoleLogger;
import io.github.appstash.memoryleak.logging.SpaceCalculator;

import java.lang.management.ManagementFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zutherb
 */
public class ProcessIdWriter implements Command {
    private static final String PROCESS_ID = "ProcessId";
    private static final Pattern PROCESS_ID_PATTERN = Pattern.compile("([0-9]+)@(.*)");
    private static final ConsoleLogger LOGGER = ConsoleLogger.getConsoleLogger();

    @Override
    public void execute() {
        LOGGER.log(getName() + getProcessId());
    }

    private String getProcessId() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        Matcher matcher = PROCESS_ID_PATTERN.matcher(name);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return name;
    }

    private String getName() {
        return SpaceCalculator.appendSpaces(PROCESS_ID) + ": ";
    }
}
