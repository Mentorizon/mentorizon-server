package com.mapthree.mentorizonserver.dto.user.create;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

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

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Years of experience is required")
    @Min(value = 0, message = "Years of experience cannot be less than 0")
    @Max(value = 50, message = "Years of experience cannot be more than 50")
    private int yearsOfExperience;

    @NotEmpty(message = "At least one domain of expertise is required")
    private List<UUID> domainIds;
}
