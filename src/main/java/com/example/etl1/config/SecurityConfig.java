package com.example.etl1.config;

import com.example.etl1.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/products", "/newegg-pcs", "/components/**",
                                       "/images/**", "/css/**", "/js/**", "/login", "/oauth2/**").permitAll()
                        .requestMatchers("/users/after-login", "/users/check/**", "/users/has-staff", "/users/{name}").permitAll()
                        .requestMatchers("/customer/**").hasAuthority("CUSTOMER")
                        .requestMatchers("/staff/**").hasAuthority("STAFF")
                        .requestMatchers("/access-denied").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/")
                        .defaultSuccessUrl("/users/after-login", true)
                        .failureUrl("/login?error")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                )
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("https://dev-nkoakipmfm3rqyah.uk.auth0.com/v2/logout?client_id=Ow4xGShneGg3b6HtGSJcLbnnxKoFkM8N&returnTo=http://localhost:8080/")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/access-denied")
                );

        return http.build();
    }
}
