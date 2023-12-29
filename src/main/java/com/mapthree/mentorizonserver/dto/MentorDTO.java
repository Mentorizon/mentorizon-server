package com.mapthree.mentorizonserver.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class MentorDTO extends UserDTO {

    @NotBlank(message = "Job title is mandatory")
    private String jobTitle;

    @NotBlank(message = "CV is mandatory")
    private String cvBase64;

    private String contactInfo; // Optional, for mentors who do not have it in CV
}
