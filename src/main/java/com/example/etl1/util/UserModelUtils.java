package com.example.etl1.util;

import com.example.etl1.repository.users.UserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;

public class UserModelUtils {

    public static void addUserAttributes(OAuth2User oauth2User, Model model, UserRepository userRepository) {
        if (oauth2User != null) {
            String name = oauth2User.getAttribute("name");
            String email = oauth2User.getAttribute("email");

            model.addAttribute("userName", name);
            model.addAttribute("userEmail", email);
            model.addAttribute("isAuthenticated", true);

            userRepository.findByEmail(email)
                    .ifPresentOrElse(
                            u -> {
                                model.addAttribute("userRole", u.getRole().name());
                                model.addAttribute("userId", u.getId()); // always set userId
                            },
                            () -> {
                                model.addAttribute("userRole", "");
                                model.addAttribute("userId", null);
                            }
                    );
        } else {
            model.addAttribute("userName", "");
            model.addAttribute("userEmail", "");
            model.addAttribute("isAuthenticated", false);
            model.addAttribute("userRole", "");
            model.addAttribute("userId", null);
        }
    }
}
