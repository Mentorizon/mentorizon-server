package com.mapthree.mentorizonserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "user_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "google_id", unique = true)
    private String googleId;

    @Column(name = "is_mentor", nullable = false)
    private boolean isMentor;

    @Column(name = "is_approved")
    private boolean isApproved;

    @Column(name = "is_mentee", nullable = false)
    private boolean isMentee;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "cv_name", unique = true)
    private String cvName;

    @Column(name = "contact_info")
    private String contactInfo;
}
