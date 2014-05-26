package io.github.appstash.task;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zutherb
 */
public abstract class AbstractLoggingTask<T> {

    private static final RestTemplate REST_TEMPLATE = createRestTemplate();
    private static final String URI_TEMPLATE = "{baseUrl}/{index}/{type}/{id}";

    private static RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJacksonHttpMessageConverter jsonMessageConverter = new MappingJacksonHttpMessageConverter();
        jsonMessageConverter.setObjectMapper(createObjectMapper());
        messageConverters.add(jsonMessageConverter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

    protected void log(T object) {
        String baseUrl = getProtocol() + "://" + getHost() + ":" + getPort();
        REST_TEMPLATE.put(URI_TEMPLATE, object, baseUrl, getIndexName(), getTypeName(), getId());
    }

    protected abstract String getTypeName();

    protected String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProtocol() {
        return "http";
    }

    public String getHost() {
        return "10.211.55.100";
    }

    private String getIndexName() {
        return "appstash-2014.05.24";
    }

    public Object getPort() {
        return 9200;
    }

    public Object getId() {
        return UUID.randomUUID().toString();
    }
}
