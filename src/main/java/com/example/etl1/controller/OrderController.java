package com.example.etl1.controller;

import com.example.etl1.model.Basket;
import com.example.etl1.model.BasketItem;
import com.example.etl1.model.Product;

import com.example.etl1.model.users.User;
import com.example.etl1.repository.BasketRepository;
import com.example.etl1.repository.logistics.OrderRepository;
import com.example.etl1.repository.users.UserRepository;
import com.example.etl1.service.BasketService;
import com.example.etl1.service.OrderService;
import com.example.etl1.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    BasketService basketService;

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

        ModelAndView mav = new ModelAndView("order_channel_table");

        if ("STAFF".equals(role)) {
            mav.addObject("orders", orderRepository.findAllWithDistribution());
        } else {
            return mav;
        }

        return mav;
    }

    @GetMapping("/checkout")
    public ModelAndView showCheckout(HttpSession session) {
        ModelAndView mav = new ModelAndView("checkout");

        Basket basket = basketRepository.findBySessionId(session.getId()).orElse(null);

        if (basket != null && !basket.getItems().isEmpty()) {
            // Use BasketItem.getTotalPrice() to sum totals
            BigDecimal total = basket.getItems().stream()
                    .map(BasketItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            mav.addObject("basket", basket);
            mav.addObject("totalPrice", total);
        } else {
            mav.addObject("basket", null);
            mav.addObject("totalPrice", BigDecimal.ZERO);
        }

        return mav;
    }


    @PostMapping("/create_order")
    public ModelAndView submitOrder(
            @RequestParam String address,
            @RequestParam Integer productId,
            @RequestParam Integer quantity,
            @ModelAttribute(name = "userId", binding = false) Long userId
    ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((OAuth2User) auth.getPrincipal()).getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        orderService.createOrder(address, productId, quantity, user);

        ModelAndView mav = new ModelAndView("orderSuccess");
        mav.addObject("message", "Order placed successfully!");
        return mav;
    }

    @PostMapping("/create_from_basket")
    public ModelAndView submitCheckout(
            @RequestParam String address,
            HttpSession session
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((OAuth2User) auth.getPrincipal()).getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Basket basket = basketRepository.findBySessionId(session.getId()).orElse(null);

        if (basket == null || basket.getItems().isEmpty()) {
            throw new RuntimeException("Basket is empty");
        }

        // Loop through each basket item and create an order line
        for (BasketItem item : basket.getItems()) {
            Integer productId = item.getProduct().getId();
            Integer quantity = item.getQuantity();
            orderService.createOrder(address, productId, quantity, user);
        }

        // Clear basket after creating all orders
        basketService.clearBasket(basket);

        // Redirect to confirmation page
        ModelAndView mav = new ModelAndView("orderSuccess");
        mav.addObject("message", "Order placed successfully!");
        return mav;
    }
}