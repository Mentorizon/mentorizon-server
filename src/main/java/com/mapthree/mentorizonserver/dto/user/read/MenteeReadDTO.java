package com.mapthree.mentorizonserver.dto.user.read;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenteeReadDTO extends UserReadDTO {
    public MenteeReadDTO(UUID id, String name, String email) {
        super(id, name, email);
    }
}
