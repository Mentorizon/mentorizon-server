package com.mapthree.mentorizonserver.service;

import com.mapthree.mentorizonserver.dto.user.read.MenteeReadDTO;
import com.mapthree.mentorizonserver.dto.user.read.MentorReadDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {
    List<MenteeReadDTO> findAllMentees(String sortBy);
    List<MentorReadDTO> findMentorsByCriteria(Boolean approved,
                                              List<String> domains,
                                              Integer yearsOfExperience,
                                              Integer rating,
                                              String sortBy);
    MentorReadDTO findMentorById(UUID mentorId);
    void approveMentor(UUID mentorId);
}
