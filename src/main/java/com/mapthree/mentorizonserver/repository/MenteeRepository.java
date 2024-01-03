package com.mapthree.mentorizonserver.repository;

import com.mapthree.mentorizonserver.model.Mentee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MenteeRepository extends JpaRepository<Mentee, UUID> {
}
