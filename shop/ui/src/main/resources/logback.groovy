import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.*
def defaultPattern = "{ \"@timestamp\":\"%d{yyyy/MM/dd'T'HH:mm:ss,SSS}\", \"thread\":\"%thread\", \"level\": \"%-5level\", \"class\" : \"%logger{36}\", \"message\" : \"%msg\"}%n"

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