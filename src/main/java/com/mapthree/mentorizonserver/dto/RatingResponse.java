package com.mapthree.mentorizonserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class RatingResponse {
    private UUID mentorId;
    private double averageRating;
    private int ratingsNumber;
}
