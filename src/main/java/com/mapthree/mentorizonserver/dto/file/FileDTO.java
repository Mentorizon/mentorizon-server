package com.mapthree.mentorizonserver.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileDTO {
    private String fileName;
    private String base64;
}
