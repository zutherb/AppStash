package io.github.appstash.memoryleak.command;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zutherb
 */
public abstract class AbstractPrinterContext {
    private static final AtomicInteger STRING_LENGTH_COUNTER = new AtomicInteger();

    protected String getSpaces() {
        StringBuilder builder = new StringBuilder();
        for (int i = getLength(); i < STRING_LENGTH_COUNTER.get(); i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    protected abstract int getLength();


    public static AtomicInteger getStringLengthCounter() {
        return STRING_LENGTH_COUNTER;
    }
}
