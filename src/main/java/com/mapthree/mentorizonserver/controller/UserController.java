package com.mapthree.mentorizonserver.controller;

import com.mapthree.mentorizonserver.dto.user.create.MenteeCreateDTO;
import com.mapthree.mentorizonserver.dto.user.create.MentorCreateDTO;
import com.mapthree.mentorizonserver.dto.user.read.MenteeReadDTO;
import com.mapthree.mentorizonserver.dto.user.read.MentorReadDTO;
import com.mapthree.mentorizonserver.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/mentees")
    public ResponseEntity<String> saveMentee(@Valid @RequestBody MenteeCreateDTO mentee) {
        userService.saveMentee(mentee);
        return ResponseEntity.ok("Mentee saved successfully.");
    }

    @PostMapping("/mentors")
    public ResponseEntity<String> saveMentor(@Valid @RequestBody MentorCreateDTO mentor) {
        userService.saveMentor(mentor);
        return ResponseEntity.ok("Mentor saved successfully.");
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
