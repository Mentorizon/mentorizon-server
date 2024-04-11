package com.mapthree.mentorizonserver.dto.mentorshipapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorshipApplicationCreateDTO {
    private UUID menteeId;
    private UUID mentorId;
    private String reason;
    private String motivation;
    private String selfDescription;
    private String currentSkills;
    private String goal;
}
