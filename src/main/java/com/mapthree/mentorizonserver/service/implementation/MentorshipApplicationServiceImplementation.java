package com.mapthree.mentorizonserver.service.implementation;

import com.mapthree.mentorizonserver.dto.mentorshipapplication.MentorshipApplicationCreateDTO;
import com.mapthree.mentorizonserver.exception.UserNotFoundException;
import com.mapthree.mentorizonserver.exception.mentorshipapplication.AlreadyMentoredException;
import com.mapthree.mentorizonserver.exception.mentorshipapplication.ApplicationPendingException;
import com.mapthree.mentorizonserver.exception.mentorshipapplication.MentorshipApplicationNotFoundException;
import com.mapthree.mentorizonserver.model.MentorshipApplication;
import com.mapthree.mentorizonserver.model.ApplicationStatus;
import com.mapthree.mentorizonserver.model.User;
import com.mapthree.mentorizonserver.repository.MentorshipApplicationRepository;
import com.mapthree.mentorizonserver.repository.UserRepository;
import com.mapthree.mentorizonserver.service.MentorshipApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MentorshipApplicationServiceImplementation implements MentorshipApplicationService {

    private final MentorshipApplicationRepository repository;
    private final UserRepository userRepository;

    public MentorshipApplicationServiceImplementation(MentorshipApplicationRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public MentorshipApplication submitApplication(MentorshipApplicationCreateDTO applicationDTO) {
        // Get all applications that are not rejected
        List<MentorshipApplication> existingApplications = repository.findByMenteeIdAndMentorIdAndStatusNot(
                applicationDTO.getMenteeId(),
                applicationDTO.getMentorId(),
                ApplicationStatus.DENIED
        );

        // If there's a pending application, throw an exception or return a custom error response
        if (existingApplications.stream().anyMatch(app -> app.getStatus() == ApplicationStatus.PENDING)) {
            throw new ApplicationPendingException("You have already submitted an application to this mentor.");
        }

        // If there's an approved application, throw an exception or return a custom error response
        if (existingApplications.stream().anyMatch(app -> app.getStatus() == ApplicationStatus.APPROVED)) {
            throw new AlreadyMentoredException("You are already being mentored by this mentor.");
        }

        // Otherwise, create a new application
        User mentee = userRepository.findById(applicationDTO.getMenteeId())
                .orElseThrow(() -> new UserNotFoundException("Mentee not found"));
        User mentor = userRepository.findById(applicationDTO.getMentorId())
                .orElseThrow(() -> new UserNotFoundException("Mentor not found"));

        MentorshipApplication application = MentorshipApplication.builder()
                .mentee(mentee)
                .mentor(mentor)
                .reason(applicationDTO.getReason())
                .motivation(applicationDTO.getMotivation())
                .selfDescription(applicationDTO.getSelfDescription())
                .currentSkills(applicationDTO.getCurrentSkills())
                .goal(applicationDTO.getGoal())
                .status(ApplicationStatus.PENDING)
                .build();

        return repository.save(application);
    }

    @Override
    public List<MentorshipApplication> getAllApplications() {
        return repository.findAll();
    }

    @Override
    public List<MentorshipApplication> getApplicationsForMentee(UUID menteeId) {
        return repository.findByMenteeId(menteeId);
    }

    @Override
    public List<MentorshipApplication> getApplicationsForMentor(UUID mentorId) {
        return repository.findByMentorId(mentorId);
    }

    @Override
    public MentorshipApplication updateApplicationStatus(UUID applicationId, ApplicationStatus status) {
        MentorshipApplication application = repository.findById(applicationId)
                .orElseThrow(() -> new MentorshipApplicationNotFoundException("Mentorship Application not found"));
        application.setStatus(status);
        return repository.save(application);
    }

    @Override
    public void deleteApplication(UUID applicationId) {
        if (!repository.existsById(applicationId)) {
            throw new MentorshipApplicationNotFoundException("Mentorship Application not found!");
        }
        repository.deleteById(applicationId);
    }

}
