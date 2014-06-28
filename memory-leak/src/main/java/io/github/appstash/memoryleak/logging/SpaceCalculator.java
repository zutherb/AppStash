package io.github.appstash.memoryleak.logging;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zutherb
 */
public final class SpaceCalculator {
    private static final AtomicInteger STRING_LENGTH_COUNTER = new AtomicInteger();

    public static synchronized String appendSpaces(String text) {
        if (text.length() > STRING_LENGTH_COUNTER.get()) {
            STRING_LENGTH_COUNTER.set(text.length());
        }
        StringBuilder builder = new StringBuilder(text);
        for (int i = text.length(); i < STRING_LENGTH_COUNTER.get(); i++) {
            builder.append(" ");
        }
        return builder.toString();
    }
}
