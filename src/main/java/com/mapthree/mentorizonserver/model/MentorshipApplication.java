package com.mapthree.mentorizonserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "mentorship_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorshipApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mentee_id")
    private User mentee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mentor_id")
    private User mentor;

    @Column(name = "reason")
    private String reason;

    @Column(name = "motivation")
    private String motivation;

    @Column(name = "self_description")
    private String selfDescription;

    @Column(name = "current_skills")
    private String currentSkills;

    @Column(name = "goal")
    private String goal;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.PENDING;
}
