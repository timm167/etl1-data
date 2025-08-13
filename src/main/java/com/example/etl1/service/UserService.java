package com.example.etl1.service;

import com.example.etl1.model.users.User;
import com.example.etl1.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

public User createOrUpdateOAuth2User(String displayName, String email) {
    // Look for existing user by email first
    Optional<User> existingUser = userRepository.findByEmail(email);

    if (existingUser.isPresent()) {
        User user = existingUser.get();
        // Update name if it has changed
        if (!user.getName().equals(displayName)) {
            user.setName(displayName);
            return userRepository.save(user);
        }
        return user;
    } else {
        // Create new user with CUSTOMER role by default
        User newUser = new User(displayName, email, User.Role.CUSTOMER);
        return userRepository.save(newUser);
    }
}

    // Method required by SecurityConfig
    public String getUserRole(String userEmail) {
        Optional<User> userOpt = userRepository.findByEmail(userEmail);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getRole().name(); // Returns "STAFF" or "CUSTOMER"
        }
        return "CUSTOMER"; // Default role for non-existent users
    }

    // Separate method for creating staff accounts
    public User createStaffAccount(String name, String email) {
        if (!userRepository.existsByEmail(email)) {
            User staffUser = new User(name, email, User.Role.STAFF);
            return userRepository.save(staffUser);
        }
        return null;
    }

    // Bootstrap method to create initial admin
    public User createBootstrapAdmin(String name, String email) {
        // Only create if no staff members exist
        if (userRepository.existsByRole(User.Role.STAFF)) {
            throw new IllegalStateException("Staff members already exist. Bootstrap not needed.");
        }

        if (userRepository.existsByEmail(email)) {
            // If user exists but is not staff, update them to staff
            User existingUser = userRepository.findByEmail(email).get();
            if (existingUser.getRole() != User.Role.STAFF) {
                existingUser.setRole(User.Role.STAFF);
                return userRepository.save(existingUser);
            }
            return existingUser;
        }

        User adminUser = new User(name, email, User.Role.STAFF);
        return userRepository.save(adminUser);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean usernameExists(String name) {
        return userRepository.existsByName(name);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean hasStaffMembers() {
        return userRepository.existsByRole(User.Role.STAFF);
    }
}