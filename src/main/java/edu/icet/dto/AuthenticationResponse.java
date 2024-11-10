package edu.icet.dto;

import edu.icet.entity.UserEntity;
import edu.icet.util.UserRole;
import jakarta.persistence.NamedEntityGraph;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String jwt;
    private UserEntity user;
}
