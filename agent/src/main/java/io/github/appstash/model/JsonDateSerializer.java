package io.github.appstash.model;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.Date;

/**
 * @author zutherb
 */
public class JsonDateSerializer extends JsonSerializer<Date> {
    private static DateTimeFormatter formatter =
            DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeString(formatter.print(value.getTime()));
    }
}
