package com.mapthree.mentorizonserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MentorSignUpDTO {

    private UUID id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should have at least 8 characters.")
    private String password;

    @NotBlank(message = "Job title is mandatory")
    private String jobTitle;

    @NotBlank(message = "CV is mandatory")
    private String cvBase64;

    private String contactInfo; // Optional, for mentors who do not have it in CV
}
