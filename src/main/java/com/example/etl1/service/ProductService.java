package com.example.etl1.service;
import com.example.etl1.model.Product;
import com.example.etl1.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return productRepository.findAll(sort);
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }
}

