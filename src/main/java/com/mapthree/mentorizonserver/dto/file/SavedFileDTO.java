package com.mapthree.mentorizonserver.dto.file;

import lombok.Data;

import java.util.Date;

@Data
public class SavedFileDTO {
    private String originalFileName;
    private String generatedFileName;
    private Date uploadedAt;
}
