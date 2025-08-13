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

    @GetMapping("/order_form")
    public ModelAndView showOrderForm() {
        List<Product> products = productService.getAllProducts();
        ModelAndView mav = new ModelAndView("order");
        mav.addObject("products", products);
        return mav;
    }

    @PostMapping("/create_order")
    public ModelAndView submitOrder(
            @RequestParam String address,
            @RequestParam Integer productId,
            @RequestParam Integer quantity
    ) {
        orderService.createOrder(address, productId, quantity);

        ModelAndView mav = new ModelAndView("orderSuccess");
        mav.addObject("message", "Order placed successfully!");
        return mav;
    }


}