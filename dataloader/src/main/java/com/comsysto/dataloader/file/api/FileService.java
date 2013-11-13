package com.comsysto.dataloader.file.api;


import java.io.InputStream;

/**
 * @author zutherb
 */
public interface FileService {
    byte[] findFileAsByteArray(String filename);

    void save(InputStream stream, String filename);
}
