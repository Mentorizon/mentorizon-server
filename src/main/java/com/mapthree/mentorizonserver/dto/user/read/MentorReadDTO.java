package com.mapthree.mentorizonserver.dto.user.read;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class MentorReadDTO extends UserReadDTO {
    private String jobTitle;
    private String cv;
    private String contactInfo;

    public MentorReadDTO(UUID id, String name, String email, String jobTitle, String cvName, String contactInfo) {
        super(id, name, email);
        this.jobTitle = jobTitle;
        this.cv = cvName;
        this.contactInfo = contactInfo;
    }
}
