package com.mapthree.mentorizonserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleName name;
}

