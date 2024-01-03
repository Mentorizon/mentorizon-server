package com.mapthree.mentorizonserver.repository;

import com.mapthree.mentorizonserver.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, UUID> {
    List<Mentor> findByIsApproved(boolean isApproved);
}
