package com.mapthree.mentorizonserver.security.auth;

import com.mapthree.mentorizonserver.dto.file.FileDTO;
import com.mapthree.mentorizonserver.dto.file.SavedFileDTO;
import com.mapthree.mentorizonserver.dto.user.create.MenteeCreateDTO;
import com.mapthree.mentorizonserver.dto.user.create.MentorCreateDTO;
import com.mapthree.mentorizonserver.dto.user.create.UserCreateDTO;
import com.mapthree.mentorizonserver.exception.EmailInUseException;
import com.mapthree.mentorizonserver.exception.SignupInformationException;
import com.mapthree.mentorizonserver.exception.UserNotFoundException;
import com.mapthree.mentorizonserver.exception.user.UserBlockedException;
import com.mapthree.mentorizonserver.model.*;
import com.mapthree.mentorizonserver.repository.DomainRepository;
import com.mapthree.mentorizonserver.repository.MentorDetailsRepository;
import com.mapthree.mentorizonserver.repository.RoleRepository;
import com.mapthree.mentorizonserver.repository.UserRepository;
import com.mapthree.mentorizonserver.security.auth.request.AuthenticationRequest;
import com.mapthree.mentorizonserver.security.auth.response.AuthenticationResponse;
import com.mapthree.mentorizonserver.security.auth.response.RegistrationResponse;
import com.mapthree.mentorizonserver.security.config.JwtService;
import com.mapthree.mentorizonserver.service.FileManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for handling user authentication and registration.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final FileManagerService fileManager;
    private final RoleRepository roleRepository;
    private final MentorDetailsRepository mentorDetailsRepository;
    private final DomainRepository domainRepository;

    public ResponseEntity<RegistrationResponse> registerMentee(@Valid @RequestBody MenteeCreateDTO dto) {
        checkIfUserExists(dto.getEmail());
        User newMentee = createNewMentee(dto);
        userRepository.save(newMentee);

        RegistrationResponse response = new RegistrationResponse("Registration successful!");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<RegistrationResponse> registerMentor(@Valid @RequestBody MentorCreateDTO dto) {
        checkIfUserExists(dto.getEmail());

        MentorDetails newMentorDetails = createNewMentorDetails(dto);
        mentorDetailsRepository.save(newMentorDetails);

        User newMentor = createNewMentor(dto);
        newMentor.setMentorDetails(newMentorDetails);
        userRepository.save(newMentor);

        RegistrationResponse response = new RegistrationResponse("Registration successful!");
        return ResponseEntity.ok(response);
    }

    private User createNewMentee(MenteeCreateDTO dto) {
        Role menteeRole = roleRepository.findById(RoleName.ROLE_MENTEE)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        User newMentee = new User(dto.getName(), dto.getEmail(), Collections.singleton(menteeRole));
        setSignUpInfo(newMentee, dto);

        return newMentee;
    }

    private void checkIfUserExists(String email) {
        if(userRepository.findByEmail(email).isPresent())
            throw new EmailInUseException("User with this email already exists.");
    }

    private User createNewMentor(MentorCreateDTO dto) {
        Role mentorRole = roleRepository.findById(RoleName.ROLE_MENTOR)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        User newMentor = new User(dto.getName(), dto.getEmail(), Collections.singleton(mentorRole));
        setSignUpInfo(newMentor, dto);

        return newMentor;
    }

    private MentorDetails createNewMentorDetails(MentorCreateDTO dto) {
        MentorDetails mentorDetails = new MentorDetails();
        mentorDetails.setJobTitle(dto.getJobTitle());

        String cvName = getCvName(dto);
        mentorDetails.setCvName(cvName);

        if(dto.getContactInfo() != null)
            mentorDetails.setContactInfo(dto.getContactInfo());

        mentorDetails.setDescription(dto.getDescription());
        mentorDetails.setYearsOfExperience(dto.getYearsOfExperience());

        Set<Domain> mentorDomains = dto.getDomainIds().stream()
                .map(domainRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        mentorDetails.setDomains(mentorDomains);

        return mentorDetails;
    }

    private String getCvName(MentorCreateDTO mentor) {
        FileDTO fileDTO = new FileDTO(mentor.getName() + ".pdf", mentor.getCvBase64());
        SavedFileDTO savedFileDTO = fileManager.uploadFile(fileDTO);
        return savedFileDTO.getGeneratedFileName();
    }

    private void setSignUpInfo(User user, UserCreateDTO dto) {
        if(dto.getPassword() != null)
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        else if(dto.getGoogleId() != null)         // TODO: Additional logic for handling Google Sign-In
            user.setGoogleId(dto.getGoogleId());
        else
            throw new SignupInformationException("Missing or invalid signup information.");
    }

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        User user = userOptional.orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.isBlocked()) {
            throw new UserBlockedException("Access denied. Your account has been blocked.");
        }

        var jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(jwtToken)
                .build());
    }
}
