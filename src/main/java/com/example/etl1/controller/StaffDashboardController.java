package com.example.etl1.controller;

import com.example.etl1.model.users.User;
import com.example.etl1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/staff")
public class StaffDashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String staffProfile(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        if (oauth2User != null) {
            String email = oauth2User.getAttribute("email");
            Optional<User> user = userService.findByEmail(email);

            if (user.isPresent() && user.get().getRole() == User.Role.STAFF) {
                model.addAttribute("user", user.get());
                return "staff/profile";
            }
        }
        return "redirect:/access-denied";
    }

    @GetMapping("/dashboard")
    public String staffDashboard(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        if (oauth2User != null) {
            String email = oauth2User.getAttribute("email");
            Optional<User> user = userService.findByEmail(email);

            if (user.isPresent() && user.get().getRole() == User.Role.STAFF) {
                model.addAttribute("user", user.get());
                return "staff/dashboard";
            }
        }
        return "redirect:/access-denied";
    }

    @PostMapping("/create-account")
    public ResponseEntity<?> createStaffAccount(
            @RequestParam String name,
            @RequestParam String email,
            @AuthenticationPrincipal OAuth2User oauth2User) {

        if (oauth2User == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Must be logged in");
        }

        String creatorEmail = oauth2User.getAttribute("email");
        Optional<User> creator = userService.findByEmail(creatorEmail);

        if (creator.isEmpty() || creator.get().getRole() != User.Role.STAFF) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only existing staff members can create staff accounts");
        }

        try {
            User newStaff = userService.createStaffAccount(name, email);
            return ResponseEntity.ok(newStaff);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
