package com.mapthree.mentorizonserver.security.auth;

import com.mapthree.mentorizonserver.dto.user.create.MenteeCreateDTO;
import com.mapthree.mentorizonserver.dto.user.create.MentorCreateDTO;
import com.mapthree.mentorizonserver.security.auth.request.AuthenticationRequest;
import com.mapthree.mentorizonserver.security.auth.response.AuthenticationResponse;
import com.mapthree.mentorizonserver.security.auth.response.RegistrationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling authentication and registration requests.
 */
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register/mentee")
    public ResponseEntity<RegistrationResponse> saveMentee(@Valid @RequestBody MenteeCreateDTO mentee) {
        return service.registerMentee(mentee);
    }

    @PostMapping("/register/mentor")
    public ResponseEntity<RegistrationResponse> saveMentor(@Valid @RequestBody MentorCreateDTO mentor) {
        return service.registerMentor(mentor);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return service.authenticate(request);
    }

}
