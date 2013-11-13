import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.*

def defaultPattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"

appender("stdout", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = defaultPattern
    }
}

logger("org.apache.wicket", INFO)
logger("org.springframework.web.client", ERROR)
logger("org.springframework", INFO)
logger("com.comsysto", INFO)
logger("com.comsysto.shop.service.recommendation.impl", INFO)

root(ERROR, ["stdout"])