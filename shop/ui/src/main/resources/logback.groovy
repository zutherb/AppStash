import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender
import net.logstash.logback.encoder.LogstashEncoder

import static ch.qos.logback.classic.Level.ERROR
import static ch.qos.logback.classic.Level.INFO

appender("stdout", ConsoleAppender) {
    encoder(LogstashEncoder) {
    }
}

appender("appstash", FileAppender) {
    file = "appstash.log"
    append = true
    encoder(PatternLayoutEncoder) {
        pattern = "%msg%n"
    }
}

logger("org.apache.wicket", INFO)
logger("org.springframework.web.client", ERROR)
logger("org.springframework", INFO)
logger("com.comsysto", INFO)
logger("com.comsysto.shop.service.recommendation.impl", INFO)
logger("io.github.appstash", INFO, ["appstash"])

root(ERROR, ["stdout"])