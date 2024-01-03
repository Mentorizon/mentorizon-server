package com.mapthree.mentorizonserver.dto.user.create;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorCreateDTO extends UserCreateDTO {

    @NotBlank(message = "Job title is mandatory")
    private String jobTitle;

    @NotBlank(message = "CV is mandatory")
    private String cvBase64;

    private String contactInfo; // Optional, for mentors who do not have it in CV
}
