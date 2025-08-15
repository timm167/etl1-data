package com.example.etl1.controller;

import com.example.etl1.model.Staff;
import com.example.etl1.model.users.User;
import com.example.etl1.repository.StaffRepository;
import com.example.etl1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/customer/dashboard")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public String customerDashboard() {
        return "customer/dashboard";
    }

    // Staff dashboard - GlobalControllerAdvice handles user data
    @GetMapping("/staff/dashboard")
    @PreAuthorize("hasAuthority('STAFF')")
    public String staffDashboard() {
        return "staff/dashboard";
    }

    @GetMapping("/after-login")
    public String afterLogin(@AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User != null) {

            String email = (String) oauth2User.getAttributes().get("email");
            String username = oauth2User.getName();
            Optional<Staff> staff = staffRepository.findByEmail(email);

            if (staff.isPresent()) {
                userService.createStaffAccount(username, email);
            } else {
                userService.createOrUpdateOAuth2User(username, email);
            }

            Optional<User> userOpt = userService.findByEmail(email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (user.getRole() == User.Role.STAFF) {
                    return "redirect:/users/staff/dashboard";
                } else {
                    return "redirect:/users/customer/dashboard";
                }
            }
        }
        return "redirect:/";
    }

    // Endpoints for checking user data
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
