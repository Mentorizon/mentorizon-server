package com.mapthree.mentorizonserver.service.implementation;

import com.mapthree.mentorizonserver.dto.file.FileDTO;
import com.mapthree.mentorizonserver.dto.file.SavedFileDTO;
import com.mapthree.mentorizonserver.dto.user.create.MenteeCreateDTO;
import com.mapthree.mentorizonserver.dto.user.create.MentorCreateDTO;
import com.mapthree.mentorizonserver.dto.user.create.UserCreateDTO;
import com.mapthree.mentorizonserver.dto.user.read.MenteeReadDTO;
import com.mapthree.mentorizonserver.dto.user.read.MentorReadDTO;
import com.mapthree.mentorizonserver.exception.EmailInUseException;
import com.mapthree.mentorizonserver.exception.SignupInformationException;
import com.mapthree.mentorizonserver.model.*;
import com.mapthree.mentorizonserver.repository.DomainRepository;
import com.mapthree.mentorizonserver.repository.MentorDetailsRepository;
import com.mapthree.mentorizonserver.repository.RoleRepository;
import com.mapthree.mentorizonserver.repository.UserRepository;
import com.mapthree.mentorizonserver.service.FileManagerService;
import com.mapthree.mentorizonserver.service.UserService;
import com.mapthree.mentorizonserver.specification.MentorSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final DomainRepository domainRepository;
    private final FileManagerService fileManager;
    private final RoleRepository roleRepository;
    private final MentorDetailsRepository mentorDetailsRepository;

    public UserServiceImplementation(UserRepository userRepository, DomainRepository domainRepository,
                                     FileManagerService fileManager, RoleRepository roleRepository, MentorDetailsRepository mentorDetailsRepository) {
        this.userRepository = userRepository;
        this.domainRepository = domainRepository;
        this.fileManager = fileManager;
        this.roleRepository = roleRepository;
        this.mentorDetailsRepository = mentorDetailsRepository;
    }
    // private final PasswordEncoder passwordEncoder;  // TODO: uncomment security dependency and implement password encoding


    @Override
    public void saveMentee(MenteeCreateDTO dto) {
        checkIfUserExists(dto.getEmail());
        User newMentee = createNewMentee(dto);
        userRepository.save(newMentee);
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

    @Override
    public void saveMentor(MentorCreateDTO dto) {
        checkIfUserExists(dto.getEmail());

        MentorDetails newMentorDetails = createNewMentorDetails(dto);
        mentorDetailsRepository.save(newMentorDetails);

        User newMentor = createNewMentor(dto);
        newMentor.setMentorDetails(newMentorDetails);
        userRepository.save(newMentor);
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
            user.setPassword(dto.getPassword());
        else if(dto.getGoogleId() != null)         // TODO: Additional logic for handling Google Sign-In
            user.setGoogleId(dto.getGoogleId());
        else
            throw new SignupInformationException("Missing or invalid signup information.");
    }

    @Override
    public List<MenteeReadDTO> findAllMentees() {
        List<User> mentees = userRepository.findByRole(RoleName.ROLE_MENTEE);
        return convertToMenteeDTOList(mentees);
    }

    @Override
    public List<MentorReadDTO> findApprovedMentors() {
        List<User> mentors = userRepository.findMentorsByApproved(true);
        return convertToMentorDTOList(mentors);
    }

    @Override
    public List<MentorReadDTO> findNotApprovedMentors() {
        List<User> mentors = userRepository.findMentorsByApproved(false);
        return convertToMentorDTOList(mentors);
    }

    @Override
    public List<MentorReadDTO> findMentorsByCriteria(List<String> domains, Integer yearsOfExperience, Integer rating) {
        Specification<User> spec = Specification.where(MentorSpecification.isApproved());

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

        List<User> mentorUsers = userRepository.findAll(spec);
        return convertToMentorDTOList(mentorUsers);
    }

    private List<MenteeReadDTO> convertToMenteeDTOList(List<User> mentees) {
        return mentees.stream()
                .map(this::convertToMenteeDTO)
                .collect(Collectors.toList());
    }

    private MenteeReadDTO convertToMenteeDTO(User mentee) {
        return new MenteeReadDTO(mentee.getId(), mentee.getName(), mentee.getEmail());
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

        return new MentorReadDTO(mentor.getId(), mentor.getName(), mentor.getEmail(), mentorDetails.getJobTitle(),
                mentorDetails.getDescription(), mentorDetails.getYearsOfExperience(), domainNames,
                mentorDetails.getCvName(), mentorDetails.getContactInfo(), mentorDetails.getRating());
    }

}