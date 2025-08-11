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

    // OAuth2 users are always customers
    public User createOrUpdateOAuth2User(OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        if (email == null || name == null) {
            throw new IllegalArgumentException("OAuth2 user missing required fields");
        }

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (!user.getName().equals(name)) {
                user.setName(name);
                return userRepository.save(user);
            }
            return user;
        } else {
            // OAuth2 users are always customers
            User newUser = new User(name, email, User.Role.CUSTOMER);
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
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email already exists: " + email);
        }
        if (userRepository.existsByName(name)) {
            throw new IllegalArgumentException("User with name already exists: " + name);
        }

        User staffUser = new User(name, email, User.Role.STAFF);
        return userRepository.save(staffUser);
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