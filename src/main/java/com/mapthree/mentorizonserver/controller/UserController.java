package com.mapthree.mentorizonserver.controller;

import com.mapthree.mentorizonserver.dto.MenteeSignUpDTO;
import com.mapthree.mentorizonserver.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/mentees")
    public ResponseEntity<String> saveMentee(@Valid @RequestBody MenteeSignUpDTO mentee) {
        userService.saveMentee(mentee);
        return ResponseEntity.ok("Mentee saved successfully.");
    }
}
