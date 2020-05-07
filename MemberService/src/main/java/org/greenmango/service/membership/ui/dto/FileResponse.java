package org.greenmango.service.membership.ui.dto;

import org.springframework.core.io.Resource;

import java.io.Serializable;

public class FileResponse implements Serializable {
    private final Resource resource;
    private final String fileName;
    private final String fileType;

    public FileResponse(Resource resource, String fileName, String fileType) {
        this.resource = resource;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public Resource getResource() {
        return resource;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    @Override
    public String toString() {
        return "FileResponse{" +
                 " fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
