package org.greenmango.service.membership.controller;

import org.greenmango.service.membership.service.FileProcessor;
import org.greenmango.service.membership.service.IFileProcessor;
import org.greenmango.service.membership.ui.dto.BaseRequest;
import org.greenmango.service.membership.ui.dto.FileResponse;
import org.greenmango.service.membership.ui.dto.ImageRequest;
import org.greenmango.service.membership.util.DAOConverters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * File uploading and downloading
 */
@RestController
public class FileUploadController {
    private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    private final IFileProcessor fileProcessor;

    @Autowired
    public FileUploadController(IFileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    /**
     * @param file multiplartfile to be uploaded
     * @return ImageRequest with Base64 String
     */
    @PostMapping("/upload")
    public BaseRequest<ImageRequest> upload(@RequestParam("file") MultipartFile file) {
        return new BaseRequest(DAOConverters.convertImage().apply(file));
    }

    /**
     * @param id
     * @return Send file to Browser to download
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Integer id) {
        FileResponse file = fileProcessor.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\"" + file.getFileName() + "\"")
                .body(file.getResource());

    }


}
