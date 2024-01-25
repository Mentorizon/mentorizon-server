package com.mapthree.mentorizonserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "mentor_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "cv_name", unique = true)
    private String cvName;

    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "description")
    private String description;

    @Column(name = "years_of_experience")
    private int yearsOfExperience;

    @ManyToMany
    @JoinTable(
            name = "user_domain",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "domain_id")
    )
    private Set<Domain> domains;

    @Column(name = "rating")
    private int rating;

    @Column(name = "is_approved")
    private boolean isApproved;
}
