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
import com.mapthree.mentorizonserver.model.Mentee;
import com.mapthree.mentorizonserver.model.Mentor;
import com.mapthree.mentorizonserver.model.User;
import com.mapthree.mentorizonserver.repository.MenteeRepository;
import com.mapthree.mentorizonserver.repository.MentorRepository;
import com.mapthree.mentorizonserver.repository.UserRepository;
import com.mapthree.mentorizonserver.service.FileManagerService;
import com.mapthree.mentorizonserver.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;
    private final MenteeRepository menteeRepository;
    private final FileManagerService fileManager;

    public UserServiceImplementation(UserRepository userRepository, MentorRepository mentorRepository,
                                     MenteeRepository menteeRepository, FileManagerService fileManager) {
        this.userRepository = userRepository;
        this.mentorRepository = mentorRepository;
        this.menteeRepository = menteeRepository;
        this.fileManager = fileManager;
    }
    // private final PasswordEncoder passwordEncoder;  // TODO: uncomment security dependency and implement password encoding


    @Override
    public void saveMentee(MenteeCreateDTO mentee) {
        if(userRepository.findByEmail(mentee.getEmail()).isPresent())
            throw new EmailInUseException("User with this email already exists.");

        Mentee newMentee = createNewMentee(mentee);
        userRepository.save(newMentee);
    }

    private Mentee createNewMentee(MenteeCreateDTO dto) {
        Mentee newMentee = new Mentee(dto.getName(), dto.getEmail());
        setSignUpInfo(newMentee, dto);

        return newMentee;
    }

    @Override
    public void saveMentor(MentorCreateDTO mentor) {
        if(userRepository.findByEmail(mentor.getEmail()).isPresent())
            throw new EmailInUseException("User with this email already exists.");

        Mentor newMentor = createNewMentor(mentor);
        userRepository.save(newMentor);
    }

    private Mentor createNewMentor(MentorCreateDTO dto) {
        String cvName = getCvName(dto);
        Mentor newMentor = new Mentor(dto.getName(), dto.getEmail(), dto.getJobTitle(), cvName);
        if(dto.getContactInfo() != null)
            newMentor.setContactInfo(dto.getContactInfo());
        setSignUpInfo(newMentor, dto);

        return newMentor;
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
        List<Mentee> mentees = menteeRepository.findAll();
        return mentees.stream()
                .map(this::convertToMenteeDTO)
                .collect(Collectors.toList());
    }

    private MenteeReadDTO convertToMenteeDTO(Mentee mentee) {
        return new MenteeReadDTO(mentee.getId(), mentee.getName(), mentee.getEmail());
    }

    @Override
    public List<MentorReadDTO> findAllMentors() {
        List<Mentor> mentors = mentorRepository.findAll();
        return mentors.stream()
                .map(this::convertToMentorDTO)
                .collect(Collectors.toList());
    }

    private MentorReadDTO convertToMentorDTO(Mentor mentor) {
        return new MentorReadDTO(mentor.getId(), mentor.getName(), mentor.getEmail(), mentor.getJobTitle(),
                mentor.getCvName(), mentor.getContactInfo());
    }

    @Override
    public List<MentorReadDTO> findApprovedMentors() {
        List<Mentor> mentors = mentorRepository.findByIsApproved(true);
        return mentors.stream()                         // TODO: refactor: extract
                .map(this::convertToMentorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MentorReadDTO> findNotApprovedMentors() {
        List<Mentor> mentors = mentorRepository.findByIsApproved(false);
        return mentors.stream()
                .map(this::convertToMentorDTO)
                .collect(Collectors.toList());
    }

}