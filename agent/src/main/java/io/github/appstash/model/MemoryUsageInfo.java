package io.github.appstash.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.appstash.json.JsonDateSerializer;

import java.lang.management.MemoryUsage;
import java.util.Date;

/**
 * @author zutherb
 */
public class MemoryUsageInfo extends java.lang.management.MemoryUsage {
    private final static String version = "1";

    private String name;
    private String type;
    private Date timestamp;

    private MemoryUsageInfo(MemoryUsage usage, String name, String type, Date timestamp) {
        super(usage.getInit(), usage.getUsed(), usage.getCommitted(), usage.getMax());
        this.name = name;
        this.type = type;
        this.timestamp = (timestamp != null) ? timestamp : new Date();
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public String getType() {
        return type;
    }

    @JsonProperty("@timestamp")
    @JsonSerialize(using = JsonDateSerializer.class)
    @SuppressWarnings("unused")
    public Date getTimestamp() {
        return timestamp;
    }

    @JsonProperty("@version")
    @SuppressWarnings("unused")
    public String getVersion() {
        return version;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {

        private MemoryUsage usage;
        private String name;
        private String type;

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

        public Builder timestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public MemoryUsageInfo build() {
            return new MemoryUsageInfo(usage, name, type, timestamp);
        }
    }
}
