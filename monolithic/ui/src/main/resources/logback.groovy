import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import net.logstash.logback.encoder.LogstashEncoder
import static ch.qos.logback.classic.Level.*

def defaultPattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"

if (System.properties.find { it.key == "spring.profiles.active" } != "production") {
    appender("stdout", ConsoleAppender) {
        encoder(PatternLayoutEncoder) {
            pattern = defaultPattern
        }
    }

    root(INFO, ["stdout"])
} else {
    appender("stdout", ConsoleAppender) {
        encoder(LogstashEncoder) {
        }
    }

    appender("readable", FileAppender) {
        file = "/var/log/tomcat6/appstash-readable.log"
        encoder(PatternLayoutEncoder) {
            pattern = defaultPattern
        }
    }

    appender("logstash", FileAppender) {
        file = "/var/log/tomcat6/appstash.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%msg%n"
        }
    }

    logger("io.github.appstash.task.MemoryLoggingTask", ERROR, ["stdout"])

    root(INFO, ["stdout", "readable", "logstash"])
}


