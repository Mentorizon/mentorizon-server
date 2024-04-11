package com.mapthree.mentorizonserver.controller;

import com.mapthree.mentorizonserver.dto.mentorshipapplication.MentorshipApplicationCreateDTO;
import com.mapthree.mentorizonserver.dto.mentorshipapplication.MentorshipApplicationStatusUpdateDTO;
import com.mapthree.mentorizonserver.model.MentorshipApplication;
import com.mapthree.mentorizonserver.service.MentorshipApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/applications")
public class MentorshipApplicationController {

    private final MentorshipApplicationService applicationService;

    public MentorshipApplicationController(MentorshipApplicationService mentorshipApplicationService) {
        this.applicationService = mentorshipApplicationService;
    }

    @PostMapping
    public ResponseEntity<MentorshipApplication> submitApplication(@RequestBody MentorshipApplicationCreateDTO application) {
        return ResponseEntity.ok(applicationService.submitApplication(application));
    }

    @GetMapping
    public ResponseEntity<List<MentorshipApplication>> getApplications(
            @RequestParam(required = false) UUID mentorId,
            @RequestParam(required = false) UUID menteeId)
    {
        if (mentorId == null && menteeId == null) {     // TODO: ensure the current user is admin
            return ResponseEntity.ok(applicationService.getAllApplications());
        } else if (mentorId != null) {
            return ResponseEntity.ok(applicationService.getApplicationsForMentor(mentorId));
        } else {
            return ResponseEntity.ok(applicationService.getApplicationsForMentee(menteeId));
        }
    }

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<MentorshipApplication> updateApplicationStatus(
            @PathVariable UUID applicationId,
            @RequestBody MentorshipApplicationStatusUpdateDTO status) {
        return ResponseEntity.ok(applicationService.updateApplicationStatus(applicationId, status.getStatus()));
    }

}
