package com.example.etl1.config;

import com.example.etl1.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Value("${bootstrap.admin.create:false}")
    private boolean createBootstrapAdmin;

    @Value("${bootstrap.admin.email:}")
    private String adminEmail;

    @Value("${bootstrap.admin.name:}")
    private String adminName;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!userService.hasStaffMembers() && createBootstrapAdmin) {
            if (!adminEmail.isEmpty() && !adminName.isEmpty()) {
                userService.createBootstrapAdmin(adminName, adminEmail);
                System.out.println("Bootstrap admin created: " + adminEmail);
            }
        }
    }
}