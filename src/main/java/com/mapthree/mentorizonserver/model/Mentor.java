package com.mapthree.mentorizonserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

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

    @Column(name = "is_approved")
    private boolean isApproved;

    public Mentor(String name, String email, String jobTitle, String cvName) {
        super(name, email);
        this.jobTitle = jobTitle;
        this.cvName = cvName;
        this.isApproved = false;
    }
}
