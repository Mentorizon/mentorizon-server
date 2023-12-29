package com.mapthree.mentorizonserver.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("MENTEE")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Mentee extends User {
    public Mentee(String name, String email) {
        super(name, email);
    }
}
