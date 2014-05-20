package io.github.appstash.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.lang.management.MemoryUsage;
import java.util.Date;

/**
 * @author zutherb
 */
public class AppStashMemoryUsage extends java.lang.management.MemoryUsage {
    private String name;
    private String type;
    private String host;
    private String ip;
    private Date timestamp;

    private AppStashMemoryUsage(MemoryUsage usage, String name, String type, String host, String ip, Date timestamp) {
        super(usage.getInit(), usage.getUsed(), usage.getCommitted(), usage.getMax());
        this.name = name;
        this.type = type;
        this.host = host;
        this.ip = ip;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public String getIp() {
        return ip;
    }

    @JsonProperty("@timestamp")
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getTimestamp() {
        return timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private MemoryUsage usage;
        private String name;
        private String type;
        private String host;
        private String ip;

        private Date timestamp;

        public Builder usage(MemoryUsage usage) {
            this.usage = usage;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder timestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public AppStashMemoryUsage create() {
            return new AppStashMemoryUsage(usage, name, type, host, ip, timestamp);
        }
    }
}
