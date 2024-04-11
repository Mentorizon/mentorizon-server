package com.mapthree.mentorizonserver.controller;

import com.mapthree.mentorizonserver.dto.user.read.MenteeReadDTO;
import com.mapthree.mentorizonserver.dto.user.read.MentorReadDTO;
import com.mapthree.mentorizonserver.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/mentees")
    public ResponseEntity<List<MenteeReadDTO>> getMentees() {
        List<MenteeReadDTO> menteeDTOList = userService.findAllMentees();
        return ResponseEntity.ok(menteeDTOList);
    }

    @GetMapping("/mentors")
    public ResponseEntity<List<MentorReadDTO>> getMentors(@RequestParam(required = false) List<String> domains,
                                                          @RequestParam(required = false) Integer yearsOfExperience,
                                                          @RequestParam(required = false) Integer rating) {
        List<MentorReadDTO> mentorDTOList = userService.findMentorsByCriteria(domains, yearsOfExperience, rating);
        return ResponseEntity.ok(mentorDTOList);
    }

    @GetMapping("/mentors/approved")
    public ResponseEntity<List<MentorReadDTO>> getApprovedMentors() {
        List<MentorReadDTO> mentorDTOList = userService.findApprovedMentors();
        return ResponseEntity.ok(mentorDTOList);
    }

    @GetMapping("/mentors/not-approved")
    public ResponseEntity<List<MentorReadDTO>> getNotApprovedMentors() {
        List<MentorReadDTO> mentorDTOList = userService.findNotApprovedMentors();
        return ResponseEntity.ok(mentorDTOList);
    }
}
