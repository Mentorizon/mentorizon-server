package com.mapthree.mentorizonserver.repository;

import com.mapthree.mentorizonserver.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {
    List<Rating> findByMentorId(UUID mentorId);
    List<Rating> findByMentorIdAndMenteeId(UUID mentorId, UUID menteeId);
    int countByMentorId(UUID mentorId);
}
