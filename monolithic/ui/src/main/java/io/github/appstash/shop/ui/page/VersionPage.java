package io.github.appstash.shop.ui.page;

import net.logstash.logback.encoder.org.apache.commons.io.FileUtils;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Response;
import org.apache.wicket.response.StringResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.IOException;

@MountPath("/version")
public class VersionPage extends WebPage {

    private final static Logger LOGGER = LoggerFactory.getLogger(VersionPage.class);

    public static final ClassPathResource CLASS_PATH_RESOURCE = new ClassPathResource("/META-INF/MANIFEST.MF");

    public VersionPage() {
        Response response = newStringResponse();
        getRequestCycle().setResponse(response);
    }

    private Response newStringResponse() {
        Response response = new StringResponse();
        response.write(responseText());
        return response;
    }

    private String responseText() {
        try{
            return FileUtils.readFileToString(CLASS_PATH_RESOURCE.getFile());
        }catch (Exception e){
            LOGGER.error("Manifast could not be opened: ", e);
            throw new RuntimeException(e);
        }
    }


}