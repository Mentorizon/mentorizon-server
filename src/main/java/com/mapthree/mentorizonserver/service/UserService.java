package com.mapthree.mentorizonserver.service;

import com.mapthree.mentorizonserver.dto.MenteeSignUpDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void saveMentee(MenteeSignUpDTO mentee);
}
