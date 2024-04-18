package com.mapthree.mentorizonserver.service;

import com.mapthree.mentorizonserver.dto.RatingResponse;
import com.mapthree.mentorizonserver.model.Rating;
import org.springframework.stereotype.Service;

@Service
public interface RatingService {
    RatingResponse saveRating(Rating rating);
}
