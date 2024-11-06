package edu.icet.repository;

import edu.icet.dto.User;
import edu.icet.entity.UserEntity;
import edu.icet.util.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findFirstByEmail(String email);
    UserEntity findByUserRole(UserRole userRole);
}
