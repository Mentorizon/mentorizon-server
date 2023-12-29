package com.mapthree.mentorizonserver.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class MenteeDTO extends UserDTO {

}
