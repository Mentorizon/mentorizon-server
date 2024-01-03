package com.mapthree.mentorizonserver.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "domain")
@Data
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "domain_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;
}
