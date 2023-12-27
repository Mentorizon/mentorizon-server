package com.mapthree.mentorizonserver.service.implementation;

import com.mapthree.mentorizonserver.dto.MenteeSignUpDTO;
import com.mapthree.mentorizonserver.exception.SignupInformationException;
import com.mapthree.mentorizonserver.model.User;
import com.mapthree.mentorizonserver.repository.UserRepository;
import com.mapthree.mentorizonserver.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    // private final PasswordEncoder passwordEncoder;  // TODO: uncomment security dependency and implement password encoding

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveMentee(MenteeSignUpDTO mentee) throws SignupInformationException {
        User newUser = User.builder()
                .name(mentee.getName())
                .email(mentee.getEmail())
                .isMentee(true)
                .isMentor(false)
                .build();

        if(mentee.getPassword() != null && !mentee.getPassword().isBlank()) {
            newUser.setPassword(mentee.getPassword());
        } else if(mentee.getGoogleId() != null) {
            // TODO: Additional logic for handling Google Sign-In
            newUser.setGoogleId(mentee.getGoogleId());
        } else {
            throw new SignupInformationException("Missing or invalid signup information");
        }

        userRepository.save(newUser);
    }
}