package com.example.etl1.controller;

import com.example.etl1.model.Product;
import com.example.etl1.repository.*;
import com.example.etl1.repository.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

@Controller
public class ProductsController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public ModelAndView viewProducts(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/products");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("products", productRepository.findAll(sort));
        } else {
            modelAndView.addObject("products", productRepository.findAll());
        }

        return modelAndView;
    }
}
