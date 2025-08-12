package com.example.etl1.controller;

import com.example.etl1.model.Product;
import com.example.etl1.service.ProductService;
import com.example.etl1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {  // Now expects entity.Product
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {  // Now expects entity.Product
        return productService.getProductById(id);
    }


}