package com.mapthree.mentorizonserver.repository;

import com.mapthree.mentorizonserver.model.ApplicationStatus;
import com.mapthree.mentorizonserver.model.MentorshipApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MentorshipApplicationRepository extends JpaRepository<MentorshipApplication, UUID> {
    List<MentorshipApplication> findAll();
    List<MentorshipApplication> findByMenteeId(UUID menteeId);
    List<MentorshipApplication> findByMentorId(UUID mentorId);
    List<MentorshipApplication> findByMenteeIdAndMentorIdAndStatusNot(UUID menteeId, UUID mentorId, ApplicationStatus applicationStatus);
}