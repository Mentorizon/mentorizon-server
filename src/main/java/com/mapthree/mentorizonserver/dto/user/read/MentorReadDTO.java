package com.mapthree.mentorizonserver.dto.user.read;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class MentorReadDTO extends UserReadDTO {
    private String jobTitle;
    private String description;
    private int yearsOfExperience;
    private Set<String> domains;
    private String cv;
    private String contactInfo;
    private double rating;
    private int ratingsNumber;
    private boolean isApproved;

    public MentorReadDTO(UUID id, String name, String email, LocalDateTime createdAt, String jobTitle, String description,
                         int yearsOfExperience, Set<String> domains, String cvName, String contactInfo, double rating,
                         int ratingsNumber, boolean isApproved) {
        super(id, name, email, createdAt);
        this.jobTitle = jobTitle;
        this.description = description;
        this.yearsOfExperience = yearsOfExperience;
        this.domains = domains;
        this.cv = cvName;
        this.contactInfo = contactInfo;
        this.rating = rating;
        this.ratingsNumber = ratingsNumber;
        this.isApproved = isApproved;
    }
}
