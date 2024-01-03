package com.mapthree.mentorizonserver.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@DiscriminatorValue("MENTOR")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Mentor extends User {

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

    public Mentor(String name, String email, String jobTitle, String description, int yearsOfExperience, String cvName) {
        super(name, email);
        this.jobTitle = jobTitle;
        this.description = description;
        this.yearsOfExperience = yearsOfExperience;
        this.cvName = cvName;
        this.isApproved = false;
        this.rating = 5;
    }
}
