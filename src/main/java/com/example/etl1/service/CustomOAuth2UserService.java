package com.example.etl1.service;

import com.example.etl1.model.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        // Extract OAuth2 attributes - Auth0 specific
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        // Create/update user in database with extracted String parameters
        User user = userService.createOrUpdateOAuth2User(name, email);

        // Create authorities based on database role
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        // For Auth0, use "name" or "email" as the name attribute key
        String nameAttributeKey = "name"; // Auth0 name attribute
        if (oauth2User.getAttribute("name") == null) {
            nameAttributeKey = "email"; // Fallback to email if name not available
        }

        return new DefaultOAuth2User(authorities, oauth2User.getAttributes(), nameAttributeKey);
    }
}

