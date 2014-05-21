import net.logstash.logback.encoder.LogstashEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.*

appender("stdout", ConsoleAppender) {
    encoder(LogstashEncoder) {
    }
}

logger("org.apache.wicket", INFO)
logger("org.springframework.web.client", ERROR)
logger("org.springframework", INFO)
logger("com.comsysto", INFO)
logger("com.comsysto.shop.service.recommendation.impl", INFO)

root(ERROR, ["stdout"])