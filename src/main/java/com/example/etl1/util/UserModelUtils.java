package com.example.etl1.util;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;

public class UserModelUtils {

    public static void addUserAttributes(OAuth2User oauth2User, Model model) {
        if (oauth2User != null) {
            String name = oauth2User.getAttribute("name");
            String email = oauth2User.getAttribute("email");
            model.addAttribute("userName", name);
            model.addAttribute("userEmail", email);
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("userName", "");
            model.addAttribute("userEmail", "");
            model.addAttribute("isAuthenticated", false);
        }
    }
}