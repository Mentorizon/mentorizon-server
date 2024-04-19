package com.mapthree.mentorizonserver.controller;

import com.mapthree.mentorizonserver.dto.user.read.MenteeReadDTO;
import com.mapthree.mentorizonserver.dto.user.read.MentorReadDTO;
import com.mapthree.mentorizonserver.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/mentees")
    public ResponseEntity<List<MenteeReadDTO>> getMentees(@RequestParam(defaultValue = "createdAt") String sortBy) {
        List<MenteeReadDTO> menteeDTOList = userService.findAllMentees(sortBy);
        return ResponseEntity.ok(menteeDTOList);
    }

    @GetMapping("/mentors")
    public ResponseEntity<List<MentorReadDTO>> getMentors(@RequestParam(required = false) Boolean approved,
                                                          @RequestParam(required = false) List<String> domains,
                                                          @RequestParam(required = false) Integer yearsOfExperience,
                                                          @RequestParam(required = false) Integer rating,
                                                          @RequestParam(defaultValue = "createdAt") String sortBy) {
        List<MentorReadDTO> mentorDTOList = userService.findMentorsByCriteria(approved, domains, yearsOfExperience, rating, sortBy);
        return ResponseEntity.ok(mentorDTOList);
    }

    @GetMapping("/mentors/{mentorId}")
    public ResponseEntity<MentorReadDTO> getMentorById(@PathVariable UUID mentorId) {
        MentorReadDTO mentorDTO = userService.findMentorById(mentorId);
        return ResponseEntity.ok(mentorDTO);
    }

    @PutMapping("/mentors/{mentorId}/approve")
    public ResponseEntity<?> approveMentor(@PathVariable UUID mentorId) {
        userService.approveMentor(mentorId);
        return ResponseEntity.ok("Mentor updated successfully.");
    }

    @PostMapping("/users/{userId}/block")
    public ResponseEntity<?> blockUser(@PathVariable UUID userId) {
        userService.blockUser(userId);
        return ResponseEntity.ok("User is blocked successfully.");
    }

}
