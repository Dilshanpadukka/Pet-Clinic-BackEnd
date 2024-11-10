package edu.icet.entity;

import edu.icet.util.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String userRole;
    private String password;

    public UserEntity(String name, String email, String encodedPassword, UserRole userRole) {
        this.name = name;
        this.email = email;
        this.password = encodedPassword; // Assign the encoded password here
        this.userRole = String.valueOf(userRole);
    }

}
