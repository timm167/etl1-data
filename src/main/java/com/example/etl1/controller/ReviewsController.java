package com.example.etl1.controller;

import com.example.etl1.model.Review;
import com.example.etl1.model.users.User;
import com.example.etl1.repository.ProductRepository;
import com.example.etl1.repository.ReviewRepository;
import com.example.etl1.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class ReviewsController {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public ReviewsController(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/products/{productId}/reviews")
    public String create(@PathVariable Integer productId, @ModelAttribute(name = "userId", binding = false) Long userId, String content) {


        if (!productRepository.existsById(productId)) {
            return "redirect:/products";
        }

        Optional<User> user = userRepository.findById(userId);

        String email = user.get().getEmail();

        if (!reviewRepository.existsByProductIdAndEmail(productId, email)) {
            Review r = new Review();
            r.setProductId(productId);
            r.setEmail(email);
            r.setContent(content);
            reviewRepository.save(r);
        }

        return "redirect:/products";
    }
}