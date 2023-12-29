package com.mapthree.mentorizonserver.service.implementation;

import com.mapthree.mentorizonserver.dto.*;
import com.mapthree.mentorizonserver.exception.EmailInUseException;
import com.mapthree.mentorizonserver.exception.SignupInformationException;
import com.mapthree.mentorizonserver.model.Mentee;
import com.mapthree.mentorizonserver.model.Mentor;
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
        if(userRepository.findByEmail(mentee.getEmail()).isPresent())
            throw new EmailInUseException("User with this email already exists.");

        Mentee newMentee = createNewMentee(mentee);
        userRepository.save(newMentee);
    }

    private Mentee createNewMentee(MenteeDTO dto) {
        Mentee newMentee = new Mentee(dto.getName(), dto.getEmail());
        setSignUpInfo(newMentee, dto);

        return newMentee;
    }

    @Override
    public void saveMentor(MentorDTO mentor) {
        if(userRepository.findByEmail(mentor.getEmail()).isPresent())
            throw new EmailInUseException("User with this email already exists.");

        Mentor newMentor = createNewMentor(mentor);
        userRepository.save(newMentor);
    }

    private String getCvName(MentorDTO mentor) {
        FileDTO fileDTO = new FileDTO(mentor.getName() + ".pdf", mentor.getCvBase64());
        SavedFileDTO savedFileDTO = fileManager.uploadFile(fileDTO);
        return savedFileDTO.getGeneratedFileName();
    }

    private Mentor createNewMentor(MentorDTO dto) {
        String cvName = getCvName(dto);
        Mentor newMentor = new Mentor(dto.getName(), dto.getEmail(), dto.getJobTitle(), cvName);
        if(dto.getContactInfo() != null)
            newMentor.setContactInfo(dto.getContactInfo());
        setSignUpInfo(newMentor, dto);

        return newMentor;
    }

    private void setSignUpInfo(User user, UserDTO dto) {
        if(dto.getPassword() != null)
            user.setPassword(dto.getPassword());
        else if(dto.getGoogleId() != null)         // TODO: Additional logic for handling Google Sign-In
            user.setGoogleId(dto.getGoogleId());
        else
            throw new SignupInformationException("Missing or invalid signup information.");
    }
}