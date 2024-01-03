package com.mapthree.mentorizonserver.service;

import com.mapthree.mentorizonserver.dto.user.create.MenteeCreateDTO;
import com.mapthree.mentorizonserver.dto.user.read.MenteeReadDTO;
import com.mapthree.mentorizonserver.dto.user.create.MentorCreateDTO;
import com.mapthree.mentorizonserver.dto.user.read.MentorReadDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void saveMentee(MenteeCreateDTO mentee);
    void saveMentor(MentorCreateDTO mentor);
    List<MenteeReadDTO> findAllMentees();
    List<MentorReadDTO> findAllMentors();
    List<MentorReadDTO> findApprovedMentors();
    List<MentorReadDTO> findNotApprovedMentors();
}
