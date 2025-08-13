package com.example.etl1.controller;

import com.example.etl1.model.users.User;
import com.example.etl1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerDashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String customerDashboard(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        if (oauth2User != null) {
            String email = oauth2User.getAttribute("email");
            Optional<User> user = userService.findByEmail(email);

            if (user.isPresent()) {
                model.addAttribute("user", user.get());
                return "customer/dashboard";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        if (oauth2User != null) {
            String email = oauth2User.getAttribute("email");
            Optional<User> user = userService.findByEmail(email);
            user.ifPresent(u -> model.addAttribute("user", u));
        }
        return "customer/profile";
    }
}
