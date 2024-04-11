package com.mapthree.mentorizonserver.dto.mentorshipapplication;

import com.mapthree.mentorizonserver.model.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorshipApplicationStatusUpdateDTO {
    private ApplicationStatus status;
}

