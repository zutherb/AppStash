package io.github.appstash.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * @author zutherb
 */
public class AppStashClassLoading implements AppStashBaseObject {

    private String name;
    private String type;
    private String host;
    private String ip;

    private long totalLoadedClassCount;
    private long loadedClassCount;
    private long unloadedClassCount;

    private Date timestamp;

    private AppStashClassLoading(String name, String type, String host, String ip, long totalLoadedClassCount, long loadedClassCount, long unloadedClassCount, Date timestamp) {
        this.name = name;
        this.type = type;
        this.host = host;
        this.ip = ip;
        this.totalLoadedClassCount = totalLoadedClassCount;
        this.loadedClassCount = loadedClassCount;
        this.unloadedClassCount = unloadedClassCount;
        this.timestamp = timestamp;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getIp() {
        return ip;
    }

    public long getTotalLoadedClassCount() {
        return totalLoadedClassCount;
    }

    public long getLoadedClassCount() {
        return loadedClassCount;
    }

    public long getUnloadedClassCount() {
        return unloadedClassCount;
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


        private String name;
        private String type;
        private String host;
        private String ip;
        private long totalLoadedClassCount;
        private long loadedClassCount;
        private long unloadedClassCount;
        private Date timestamp;

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

        public Builder totalLoadedClassCount(long totalLoadedClassCount) {
            this.totalLoadedClassCount = totalLoadedClassCount;
            return this;
        }

        public Builder loadedClassCount(long loadedClassCount) {
            this.loadedClassCount = loadedClassCount;
            return this;
        }

        public Builder unloadedClassCount(long unloadedClassCount) {
            this.unloadedClassCount = unloadedClassCount;
            return this;
        }

        public Builder timestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public AppStashClassLoading create() {
            return new AppStashClassLoading(name, type, host, ip, totalLoadedClassCount, loadedClassCount, unloadedClassCount, timestamp);
        }
    }
}
