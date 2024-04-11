package com.mapthree.mentorizonserver.repository;

import com.mapthree.mentorizonserver.model.MentorDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MentorDetailsRepository extends JpaRepository<MentorDetails, UUID> {
}
