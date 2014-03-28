package com.comsysto.dataloader.file.impl;

import com.comsysto.dataloader.file.api.FileService;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;

/**
 * @author zutherb
 */
@Component
public class FileServiceImpl implements FileService {

    private GridFS gridFS;

    @Autowired
    public FileServiceImpl(GridFS gridFS) {
        this.gridFS = gridFS;
    }

    @Override
    @Cacheable(value = "files", key = "#root.methodName + #filename")
    public byte[] findFileAsByteArray(String filename) {
        GridFSDBFile gridFSFile = gridFS.findOne(filename);
        if (gridFSFile != null) {
            return IOUtils.toByteArray(gridFSFile.getInputStream());
        }
        return new byte[0];
    }

    @Override
    public void save(InputStream stream, String filename) {
        GridFSInputFile file = gridFS.createFile(stream, filename, true);
        file.save();
    }

    private static class IOUtils {

        public static byte[] toByteArray(InputStream inputStream) {
            try {
                return org.apache.commons.io.IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                throw new IllegalStateException("Could not create byte array from given input stream", e);
            }
        }
    }
}
