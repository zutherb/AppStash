package com.comsysto.shop.ui.resource;

import com.comsysto.dataloader.file.api.FileService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.wicket.extensions.markup.html.image.resource.ThumbnailImageResource;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.ByteArrayInputStream;

/**
 * @author zutherb
 */
public class MongoDBCachedThumbnailImageResource extends ThumbnailImageResource {

    @SpringBean
    private FileService fileService;

    private final ProductImageResource unscaledImageResource;
    private final int maxSize;
    private byte[] image;

    /**
     * Construct.
     *
     * @param unscaledImageResource the unscaled, original image resource. Must be not null
     * @param maxSize               maximum size (width or height) for resize operation
     */
    public MongoDBCachedThumbnailImageResource(ProductImageResource unscaledImageResource, int maxSize) {
        super(unscaledImageResource, maxSize);
        Injector.get().inject(this);
        this.unscaledImageResource = unscaledImageResource;
        this.maxSize = maxSize;
    }

    @Override
    protected byte[] getImageData(Attributes attributes) {
        String filename = unscaledImageResource.getFullProductImagePath() + "-" + maxSize;
        image = fileService.findFileAsByteArray(filename);
        if (ArrayUtils.isEmpty(image)) {
            byte[] imageData = super.getImageData(attributes);
            fileService.save(new ByteArrayInputStream(imageData), filename);
            image = imageData;
        }
        return image;
    }
}
