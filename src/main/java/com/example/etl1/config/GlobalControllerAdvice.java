package com.example.etl1.config;

import com.example.etl1.util.UserModelUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addUserAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        OAuth2User oauth2User = null;
        if (authentication != null &&
            authentication.isAuthenticated() &&
            authentication.getPrincipal() instanceof OAuth2User) {
            oauth2User = (OAuth2User) authentication.getPrincipal();
        }

        UserModelUtils.addUserAttributes(oauth2User, model);
    }
}
