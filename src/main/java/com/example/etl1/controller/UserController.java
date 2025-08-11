package com.example.etl1.controller;

import com.example.etl1.model.users.User;
import com.example.etl1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/after-login")
    public String afterLogin(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        if (oauth2User != null) {
            User user = userService.createOrUpdateOAuth2User(oauth2User);
            model.addAttribute("user", user);

            if (user.getRole() == User.Role.STAFF) {
                return "redirect:/staff/dashboard";
            } else {
                return "redirect:/customer/dashboard";
            }
        }
        return "redirect:/";
    }

    // API endpoints for checking user data
    @GetMapping("/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable String name) {
        Optional<User> user = userService.findByName(name);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/check/username/{name}")
    public ResponseEntity<Boolean> checkUsernameExists(@PathVariable String name) {
        return ResponseEntity.ok(userService.usernameExists(name));
    }

    @GetMapping("/check/email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        return ResponseEntity.ok(userService.emailExists(email));
    }

    @GetMapping("/has-staff")
    public ResponseEntity<Boolean> hasStaffMembers() {
        return ResponseEntity.ok(userService.hasStaffMembers());
    }
}