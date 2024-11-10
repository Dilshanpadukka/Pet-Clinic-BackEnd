package edu.icet.service;

import edu.icet.dto.User;

public interface AuthService {
    User createUser(User user);
    boolean hasPetOwnerWithEmail(String email);
}
