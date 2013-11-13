package com.comsysto.shop

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.*
appender("stdout", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{ABSOLUTE} %5p %c{1}:%L - %m%n"
    }
}

root(ERROR, ["stdout"])