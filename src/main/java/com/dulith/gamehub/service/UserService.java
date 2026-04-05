package com.dulith.gamehub.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dulith.gamehub.entity.Role;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User registerUser(String fullName, String email, String rawPassword) {
        if (emailExists(email)) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }

        User user = new User();
        user.setFullName(fullName.trim());
        user.setEmail(email.trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    public User updateProfile(User user) {
        return userRepository.save(user);
    }
    public void createAdminIfNotExists() {
    String adminEmail = "admin@gamehub.com";

    if (userRepository.findByEmail(adminEmail).isEmpty()) {
        User admin = new User();
        admin.setFullName("GameHub Admin");
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);
        admin.setBalance(0.0);

        userRepository.save(admin);
    }
}
}