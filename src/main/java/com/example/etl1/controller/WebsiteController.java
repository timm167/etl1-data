package com.example.etl1.controller;

import com.example.etl1.model.users.User;
import com.example.etl1.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebsiteController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView("/index");
        return modelAndView;
    }

    @PostMapping("/toggle-staff")
    public String toggleStaffRole(@ModelAttribute("userEmail") String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            if (user.getRole() == User.Role.STAFF) {
                user.setRole(User.Role.CUSTOMER);
            } else {
                user.setRole(User.Role.STAFF);
            }
            userRepository.save(user);
        });
        return "redirect:/";
    }

}
