package com.mapthree.mentorizonserver.service.implementation;

import com.mapthree.mentorizonserver.dto.RatingResponse;
import com.mapthree.mentorizonserver.model.Rating;
import com.mapthree.mentorizonserver.model.User;
import com.mapthree.mentorizonserver.repository.RatingRepository;
import com.mapthree.mentorizonserver.repository.UserRepository;
import com.mapthree.mentorizonserver.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingServiceImplementation implements RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    public RatingServiceImplementation(RatingRepository ratingRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RatingResponse saveRating(Rating rating) {
        UUID mentorId = rating.getMentorId();
        UUID menteeId = rating.getMenteeId();
        List<Rating> existingRatings = ratingRepository.findByMentorIdAndMenteeId(mentorId, menteeId);

        if (!existingRatings.isEmpty()) {
            Rating existingRating = existingRatings.get(0);
            existingRating.setStarsNumber(rating.getStarsNumber());
            ratingRepository.save(existingRating);
        } else {
            ratingRepository.save(rating);
        }

        int ratingsNumber = ratingRepository.countByMentorId(mentorId);
        double newAverageRating = updateAverageRating(mentorId);
        return new RatingResponse(mentorId, newAverageRating, ratingsNumber);
    }

    private double updateAverageRating(UUID mentorId) {
        List<Rating> ratings = ratingRepository.findByMentorId(mentorId);
        double average = ratings.stream()
                .mapToInt(Rating::getStarsNumber)
                .average()
                .orElse(0.0);

        double roundedAverage = Math.round(average * 10.0) / 10.0;

        User mentor = userRepository.findById(mentorId).orElseThrow();
        mentor.getMentorDetails().setRating(roundedAverage);
        userRepository.save(mentor);

        return roundedAverage;
    }
}
