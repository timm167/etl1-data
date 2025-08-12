package com.example.etl1.controller;

import com.example.etl1.model.Product;
import com.example.etl1.service.OrderService;
import com.example.etl1.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final ProductService productService;
    private final OrderService orderService;

    public OrderController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

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

