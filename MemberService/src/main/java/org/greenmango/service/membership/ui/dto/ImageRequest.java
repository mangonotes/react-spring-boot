package org.greenmango.service.membership.ui.dto;

import java.io.Serializable;

public class ImageRequest implements Serializable {
    private final  String file;
    private final String fileType;
    private final String fileName;
    private final String url;

    public ImageRequest(String file, String fileType, String fileName, String url) {
        this.file = file;
        this.fileType = fileType;
        this.fileName = fileName;
        this.url = url;
    }

    public String getFile() {
        return file;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileName() {
        return fileName;
    }
}
