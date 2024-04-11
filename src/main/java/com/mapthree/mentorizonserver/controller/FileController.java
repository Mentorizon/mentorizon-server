package com.mapthree.mentorizonserver.controller;

import com.mapthree.mentorizonserver.dto.file.FileDTO;
import com.mapthree.mentorizonserver.dto.file.SavedFileDTO;
import com.mapthree.mentorizonserver.service.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FileController {

    private final FileManagerService fileManager;

    @Autowired
    FileController(FileManagerService fileManager) {
        this.fileManager = fileManager;
    }

    @PostMapping("/files/upload")
    public ResponseEntity<SavedFileDTO> uploadFile(@RequestBody FileDTO fileDTO) {
        return ResponseEntity.ok(fileManager.uploadFile(fileDTO));
    }

    @GetMapping("/files/{fileName}/base64")
    public ResponseEntity<String> getFileInBase64(@PathVariable("fileName") String fileName) {
        return ResponseEntity.ok(fileManager.getFileInBase64(fileName));
    }

    @GetMapping("/files/{fileName}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) {
        byte[] content = fileManager.getFileAsBytes(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(content.length))
                .body(new ByteArrayResource(content));
    }

    @DeleteMapping("/files/{fileName:.+}")
    public ResponseEntity<Void> deleteFile(@PathVariable("fileName") String fileName) {
        fileManager.deleteFile(fileName);
        return ResponseEntity.noContent().build();
    }

}