package com.example.etl1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import com.example.etl1.service.UserService;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.client.provider.auth0.issuer-uri}")
    private String issuer;

    @Value("${spring.security.oauth2.client.registration.auth0.client-id}")
    private String clientId;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/products", "/newegg-pcs", "/components/**", "/images/**", "/css/**", "/js/**", "/users/after-login", "/users/check/**", "/users/has-staff", "/users/{name}").permitAll()
                        .requestMatchers("/customer/**").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers("/staff/**").hasAuthority("ROLE_STAFF")
                        .requestMatchers("/access-denied").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/auth0")
                        .defaultSuccessUrl("/users/after-login", false)
                        .failureUrl("/login?error")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userAuthoritiesMapper(this::mapAuthorities)
                        )
                )
                .logout(logout -> logout
                        .addLogoutHandler(logoutHandler())
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }

    @Autowired
    private UserService userService;

    private Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        // Get the current user's email from the OAuth2 context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) auth;
            String email = oauth2Token.getPrincipal().getAttribute("email");

            String role = userService.getUserRole(email);
            return List.of(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    }

    private LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {
            try {
                String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
                response.sendRedirect(issuer + "v2/logout?client_id=" + clientId + "&returnTo=" + baseUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
