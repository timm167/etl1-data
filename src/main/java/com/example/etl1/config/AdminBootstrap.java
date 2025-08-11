package com.example.etl1.config;

import com.example.etl1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Only create bootstrap admin if no staff exists and specific environment variable is set
        if (!userService.hasStaffMembers() &&
            System.getenv("CREATE_BOOTSTRAP_ADMIN") != null) {

            String adminEmail = System.getenv("BOOTSTRAP_ADMIN_EMAIL");
            String adminName = System.getenv("BOOTSTRAP_ADMIN_NAME");

            if (adminEmail != null && adminName != null) {
                try {
                    userService.createBootstrapAdmin(adminName, adminEmail);
                    System.out.println("Bootstrap admin created: " + adminEmail);
                } catch (Exception e) {
                    System.err.println("Failed to create bootstrap admin: " + e.getMessage());
                }
            }
        }
    }
}