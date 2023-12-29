package com.mapthree.mentorizonserver.service.implementation;

import com.mapthree.mentorizonserver.dto.FileDTO;
import com.mapthree.mentorizonserver.dto.MenteeDTO;
import com.mapthree.mentorizonserver.dto.MentorDTO;
import com.mapthree.mentorizonserver.dto.SavedFileDTO;
import com.mapthree.mentorizonserver.exception.EmailInUseException;
import com.mapthree.mentorizonserver.exception.SignupInformationException;
import com.mapthree.mentorizonserver.model.User;
import com.mapthree.mentorizonserver.repository.UserRepository;
import com.mapthree.mentorizonserver.service.FileManagerService;
import com.mapthree.mentorizonserver.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {


    private final UserRepository userRepository;
    private final FileManagerService fileManager;
    // private final PasswordEncoder passwordEncoder;  // TODO: uncomment security dependency and implement password encoding

    public UserServiceImplementation(UserRepository userRepository, FileManagerService fileManager) {
        this.userRepository = userRepository;
        this.fileManager = fileManager;
    }

    @Override
    public void saveMentee(MenteeDTO mentee) {
        if(userRepository.findByEmail(mentee.getEmail()).isPresent()) {
            throw new EmailInUseException("User with this email already exists.");
        }

        User newUser = User.builder()
                .name(mentee.getName())
                .email(mentee.getEmail())
                .isMentee(true)
                .build();

        if(mentee.getPassword() != null && !mentee.getPassword().isBlank()) {
            newUser.setPassword(mentee.getPassword());
        } else if(mentee.getGoogleId() != null) {
            // TODO: Additional logic for handling Google Sign-In
            newUser.setGoogleId(mentee.getGoogleId());
        } else {
            throw new SignupInformationException("Missing or invalid signup information.");
        }

        userRepository.save(newUser);
    }

    @Override
    public void saveMentor(MentorDTO mentor) {
        if(userRepository.findByEmail(mentor.getEmail()).isPresent()) {
            throw new EmailInUseException("User with this email already exists.");
        }

        User newUser = User.builder()
                .name(mentor.getName())
                .email(mentor.getEmail())
                .isMentor(true)
                .jobTitle(mentor.getJobTitle())
                .contactInfo(mentor.getContactInfo())
                .build();

        FileDTO fileDTO = new FileDTO(mentor.getName() + ".pdf", mentor.getCvBase64());
        SavedFileDTO savedFileDTO = fileManager.uploadFile(fileDTO);
        newUser.setCvName(savedFileDTO.getGeneratedFileName());

        if(mentor.getPassword() != null && !mentor.getPassword().isBlank()) {
            newUser.setPassword(mentor.getPassword());
        } else {
            throw new SignupInformationException("Missing or invalid signup information.");
        }

        userRepository.save(newUser);
    }
}