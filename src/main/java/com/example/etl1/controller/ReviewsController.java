package com.example.etl1.controller;

import com.example.etl1.model.Review;
import com.example.etl1.repository.ProductRepository;
import com.example.etl1.repository.ReviewRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ReviewsController {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewsController(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/products/{productId}/reviews")
    public String create(@PathVariable Integer productId, Principal principal, String content) {

        if (principal == null) {
            return "redirect:/login";
        }

        if (!productRepository.existsById(productId)) {
            return "redirect:/products";
        }

        String email = principal.getName(); // Authenticated User

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