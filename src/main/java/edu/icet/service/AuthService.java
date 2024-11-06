package edu.icet.service;

import edu.icet.dto.SignUpRequest;
import edu.icet.dto.User;

public interface AuthService {
    User createPetOwner(SignUpRequest signUpRequest);
    boolean hasPetOwnerWithEmail(String email);
}
