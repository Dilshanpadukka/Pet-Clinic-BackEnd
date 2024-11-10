package edu.icet.service.impl;

import edu.icet.dto.User;
import edu.icet.entity.UserEntity;
import edu.icet.repository.UserRepository;
import edu.icet.service.AuthService;
import edu.icet.util.UserRole;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;


//    @PostConstruct
//    public void createDoctorAccount() {
//        UserEntity doctorAccount = userRepository.findByUserRole(UserRole.DOCTOR);
//        if (doctorAccount == null) {
//            UserEntity newDoctorAccount = new UserEntity(
//                    "Doctor",
//                    "doctor@test.com",
//                    new BCryptPasswordEncoder().encode("doctor"),
//                    UserRole.DOCTOR
//            );
//            userRepository.save(newDoctorAccount);
//            System.out.println("Doctor Account Successfully");
//        }
//    }


    @Override
    public User createUser(User user) {
        // Create a new UserEntity with encrypted password and default role
        UserEntity userEntity = new UserEntity(
                user.getName(),
                user.getEmail(),
                new BCryptPasswordEncoder().encode(user.getPassword()),
                UserRole.PETOWNER);

        // Save the UserEntity to the repository
        userEntity = userRepository.save(userEntity);

        // Map the saved UserEntity back to a User object and return
        return mapper.map(userEntity, User.class);
    }


    @Override
    public boolean hasPetOwnerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}

//@Override
//public User createUser(User user) {
//    UserEntity userEntity = mapper.map(user, UserEntity.class);
//    return mapper.map(userRepository.save(userEntity), User.class);
//}