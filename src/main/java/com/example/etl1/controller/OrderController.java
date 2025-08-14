package com.example.etl1.controller;

import com.example.etl1.model.Product;
import com.example.etl1.repository.logistics.OrderRepository;
import com.example.etl1.repository.users.UserRepository;
import com.example.etl1.service.OrderService;
import com.example.etl1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    UserRepository UserRepository;

    @Autowired
    OrderRepository orderRepository;

    private OrderService orderService;
    private ProductService productService;

    @GetMapping("/order_form")
    public ModelAndView showOrderForm() {
        List<Product> products = productService.getAllProducts();
        ModelAndView mav = new ModelAndView("order");
        mav.addObject("products", products);
        return mav;
    }

    @GetMapping("/view")
    public ModelAndView showOrders(@ModelAttribute("userRole") String role,
                                   @ModelAttribute(name = "userId", binding = false) Long userId) {

        ModelAndView orders_mav = new ModelAndView("order_table");

        if ("STAFF".equals(role)) {
            orders_mav.addObject("orders", orderRepository.findAll());
        } else if ("CUSTOMER".equals(role)) {
            orders_mav.addObject("orders", orderRepository.findByUserId(userId.intValue()));
        }

        return orders_mav;
    }

    @GetMapping("/manage")
    public ModelAndView manageOrders(@ModelAttribute("userRole") String role,
                                   @ModelAttribute(name = "userId", binding = false) Long userId) {

        ModelAndView orders_mav = new ModelAndView("manage_order_table");

        if ("STAFF".equals(role)) {
            orders_mav.addObject("orders", orderRepository.findAll());
        } else if ("CUSTOMER".equals(role)) {
            orders_mav.addObject("orders", orderRepository.findByUserId(userId.intValue()));
        }

        return orders_mav;
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