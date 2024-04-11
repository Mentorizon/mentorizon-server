package com.mapthree.mentorizonserver.service;

import com.mapthree.mentorizonserver.dto.mentorshipapplication.MentorshipApplicationCreateDTO;
import com.mapthree.mentorizonserver.model.ApplicationStatus;
import com.mapthree.mentorizonserver.model.MentorshipApplication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface MentorshipApplicationService {
    MentorshipApplication submitApplication(MentorshipApplicationCreateDTO application);
    List<MentorshipApplication> getAllApplications();
    List<MentorshipApplication> getApplicationsForMentee(UUID menteeId);
    List<MentorshipApplication> getApplicationsForMentor(UUID mentorId);
    MentorshipApplication updateApplicationStatus(UUID applicationId, ApplicationStatus status);
    void deleteApplication(UUID applicationId);
}
