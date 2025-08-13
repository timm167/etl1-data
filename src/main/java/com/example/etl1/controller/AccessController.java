package com.example.etl1.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessController {

    @GetMapping("/access-denied")
    public String accessDenied(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        return "access-denied";
    }
}