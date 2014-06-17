package io.github.appstash.memoryleak.printer;

import java.lang.management.ManagementFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zutherb
 */
public class ProcessIdPrinter extends AbstractPrinterContext implements ConsolePrinter {
    private static final String PROCESS_ID = "ProcessId";
    private static final Pattern PROCESS_ID_PATTERN = Pattern.compile("([0-9]+)@(.*)");

    @Override
    public void print() {
        System.out.println(getName() + getProcessId());
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
        return PROCESS_ID + getSpaces() + ": ";
    }

    @Override
    protected int getLength() {
        return PROCESS_ID.length();
    }
}
