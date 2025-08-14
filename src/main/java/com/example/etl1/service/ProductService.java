package com.example.etl1.service;

import com.example.etl1.model.Product;  // Changed from entity to model
import com.example.etl1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        // Convert Long to Integer since model.Product uses Integer id
        return productRepository.findById(id.intValue());
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}