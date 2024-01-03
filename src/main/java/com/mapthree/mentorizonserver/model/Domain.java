package com.mapthree.mentorizonserver.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "domain")
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "domain_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;
}
