package com.mapthree.mentorizonserver.service.implementation;

import com.mapthree.mentorizonserver.dto.user.read.MenteeReadDTO;
import com.mapthree.mentorizonserver.dto.user.read.MentorReadDTO;
import com.mapthree.mentorizonserver.exception.UserNotFoundException;
import com.mapthree.mentorizonserver.model.*;
import com.mapthree.mentorizonserver.repository.MentorDetailsRepository;
import com.mapthree.mentorizonserver.repository.UserRepository;
import com.mapthree.mentorizonserver.service.UserService;
import com.mapthree.mentorizonserver.specification.MentorSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final MentorDetailsRepository mentorDetailsRepository;

    public UserServiceImplementation(UserRepository userRepository, MentorDetailsRepository mentorDetailsRepository) {
        this.userRepository = userRepository;
        this.mentorDetailsRepository = mentorDetailsRepository;
    }

    @Override
    public List<MenteeReadDTO> findAllMentees() {
        List<User> mentees = userRepository.findByRole(RoleName.ROLE_MENTEE);
        return convertToMenteeDTOList(mentees);
    }

    @Override
    public List<MentorReadDTO> findMentorsByCriteria(
            Boolean approved,
            List<String> domains,
            Integer yearsOfExperience,
            Integer rating,
            String sortBy)
    {
        Specification<User> spec = Specification.where(null);

        if (approved != null && approved) {
            spec = spec.and(MentorSpecification.isApproved());
        } else if (approved != null) {
            spec = spec.and(MentorSpecification.isNotApproved());
        }

        if (domains != null) {
            for (String domainId : domains) {
                spec = spec.and(MentorSpecification.hasDomain(UUID.fromString(domainId)));
            }
        }

        if (yearsOfExperience != null) {
            spec = spec.and(MentorSpecification.hasYearsOfExperience(yearsOfExperience));
        }

        if (rating != null) {
            spec = spec.and(MentorSpecification.hasRating(rating));
        }

        List<User> mentorUsers = userRepository.findAll(spec, Sort.by(Sort.Direction.ASC, sortBy));
        return convertToMentorDTOList(mentorUsers);
    }

    @Override
    public void approveMentor(UUID mentorId) {
        Optional<User> mentor = userRepository.findById(mentorId);
        if (mentor.isPresent()) {
            MentorDetails mentorDetails = mentor.get().getMentorDetails();
            mentorDetails.setApproved(true);
            mentorDetailsRepository.save(mentorDetails);
        } else {
            throw new UserNotFoundException("Mentor not found.");
        }
    }

    private List<MenteeReadDTO> convertToMenteeDTOList(List<User> mentees) {
        return mentees.stream()
                .map(this::convertToMenteeDTO)
                .collect(Collectors.toList());
    }

    private MenteeReadDTO convertToMenteeDTO(User mentee) {
        return new MenteeReadDTO(mentee.getId(), mentee.getName(), mentee.getEmail(), mentee.getCreatedAt());
    }

    private List<MentorReadDTO> convertToMentorDTOList(List<User> mentors) {
        return mentors.stream()
                .map(this::convertToMentorDTO)
                .collect(Collectors.toList());
    }

    private MentorReadDTO convertToMentorDTO(User mentor) {
        MentorDetails mentorDetails = mentor.getMentorDetails();
        Set<String> domainNames = mentorDetails.getDomains().stream()
                .map(Domain::getName)
                .collect(Collectors.toSet());

        return new MentorReadDTO(mentor.getId(), mentor.getName(), mentor.getEmail(), mentor.getCreatedAt(),
                mentorDetails.getJobTitle(), mentorDetails.getDescription(), mentorDetails.getYearsOfExperience(),
                domainNames, mentorDetails.getCvName(), mentorDetails.getContactInfo(), mentorDetails.getRating(),
                mentorDetails.isApproved());
    }

}