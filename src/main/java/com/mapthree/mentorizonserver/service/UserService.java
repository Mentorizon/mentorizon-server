package com.mapthree.mentorizonserver.service;

import com.mapthree.mentorizonserver.dto.MenteeSignUpDTO;
import com.mapthree.mentorizonserver.exception.SignupInformationException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void saveMentee(MenteeSignUpDTO mentee) throws SignupInformationException;
}
