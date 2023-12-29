package com.mapthree.mentorizonserver.service;

import com.mapthree.mentorizonserver.dto.MenteeDTO;
import com.mapthree.mentorizonserver.dto.MentorDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void saveMentee(MenteeDTO mentee);
    void saveMentor(MentorDTO mentor);
}
