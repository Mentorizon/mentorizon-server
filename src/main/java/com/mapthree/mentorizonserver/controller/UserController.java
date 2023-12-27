package com.mapthree.mentorizonserver.controller;

import com.mapthree.mentorizonserver.dto.MenteeSignUpDTO;
import com.mapthree.mentorizonserver.exception.SignupInformationException;
import com.mapthree.mentorizonserver.service.UserService;
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
    public ResponseEntity<String> saveMentee(@RequestBody MenteeSignUpDTO mentee) {
        try {
            userService.saveMentee(mentee);
        } catch (SignupInformationException e) {
            e.printStackTrace();  // TODO: research logging possibilities
            return ResponseEntity.badRequest().body("Failed to save mentee.");
        }
        return ResponseEntity.ok("Mentee saved successfully.");
    }
}
