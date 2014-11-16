package io.github.zutherb.appstash.shop.ui.page;

import io.github.zutherb.appstash.shop.ui.application.ShopApplication;
import io.github.zutherb.appstash.shop.ui.application.ShopApplication;
import net.logstash.logback.encoder.org.apache.commons.io.FileUtils;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.File;

@MountPath("/version")
public class VersionPage extends WebPage {

    private final static Logger LOGGER = LoggerFactory.getLogger(VersionPage.class);
    private static final String MANIFEST_MF_PATH = "/META-INF/MANIFEST.MF";

    public VersionPage() {
    }

    private WebResponse newStringResponse() {
        WebResponse response = (WebResponse) getResponse();
        response.setContentType("text/plain");
        response.write(responseText());
        response.flush();
        response.setStatus(HttpStatus.OK.value());
        return response;
    }

    private String responseText() {
        try {
            String realPath = ShopApplication.get().getServletContext().getRealPath(MANIFEST_MF_PATH);
            return FileUtils.readFileToString(new File(realPath));
        } catch (Exception e) {
            LOGGER.error("Manifast could not be opened: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void renderPage() {
        WebResponse response = newStringResponse();
        getRequestCycle().setResponse(response);
    }


}