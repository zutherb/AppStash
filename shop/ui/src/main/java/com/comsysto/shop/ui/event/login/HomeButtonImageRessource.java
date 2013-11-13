package com.comsysto.shop.ui.event.login;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @author zutherb
 */
public class HomeButtonImageRessource extends DynamicImageResource {

    private static final ClassPathResource RESOURCE = new ClassPathResource("/assets/img/Logo-10.png");

    @Override
    protected byte[] getImageData(Attributes attributes) {
        try {
            return FileUtils.readFileToByteArray(RESOURCE.getFile());
        } catch (IOException e) {
            throw new IllegalStateException("file not found", e);
        }
    }
}
