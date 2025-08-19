package com.example.etl1.controller;

import com.example.etl1.dto.OrderFormDto;
import com.example.etl1.model.Basket;
import com.example.etl1.model.BasketItem;
import com.example.etl1.model.Product;

import com.example.etl1.model.logistics.Order;
import com.example.etl1.model.users.User;
import com.example.etl1.repository.BasketRepository;
import com.example.etl1.repository.logistics.OrderRepository;
import com.example.etl1.repository.users.UserRepository;
import com.example.etl1.service.BasketService;
import com.example.etl1.service.CountryService;
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

    @Autowired
    CountryService countryService;

    @GetMapping("/order_form")
    public ModelAndView showOrderForm() {
        List<Product> products = productService.getAllProducts();
        ModelAndView mav = new ModelAndView("order");
        mav.addObject("products", products);
        mav.addObject("countries", countryService.getAll());
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

        mav.addObject("countries", countryService.getAll());

        if (basket != null && !basket.getItems().isEmpty()) {
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
            @ModelAttribute OrderFormDto orderForm,
            @ModelAttribute(name = "userId", binding = false) Long userId
    ) {

        String address = orderForm.getFullAddress();
        Integer productId = orderForm.getProductId();
        Integer quantity = orderForm.getQuantity();
        String recipient = orderForm.getFullName();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((OAuth2User) auth.getPrincipal()).getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        orderService.createOrder(address, productId, quantity, user, recipient);

        ModelAndView mav = new ModelAndView("orderSuccess");
        mav.addObject("message", "Order placed successfully!");
        return mav;
    }

    @PostMapping("/create_from_basket")
    public ModelAndView submitCheckout(
            @ModelAttribute OrderFormDto orderForm,
            HttpSession session
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((OAuth2User) auth.getPrincipal()).getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Basket basket = basketRepository.findBySessionId(session.getId()).orElse(null);

        String address = orderForm.getFullAddress();
        String recipient = orderForm.getFullName();


        if (basket == null || basket.getItems().isEmpty()) {
            throw new RuntimeException("Basket is empty");
        }

        for (BasketItem item : basket.getItems()) {
            Integer productId = item.getProduct().getId();
            Integer quantity = item.getQuantity();
            orderService.createOrder(address, productId, quantity, user, recipient);
        }

        basketService.clearBasket(basket);

        ModelAndView mav = new ModelAndView("orderSuccess");
        mav.addObject("message", "Order placed successfully!");
        return mav;
    }


    @PostMapping("/mark-delivered")
    public ModelAndView submitMarkDelivered(Long orderId) {

        ModelAndView mav = new ModelAndView("order_channel_table");

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new RuntimeException("Order not found");
        } else {
            order.setIsOpen(Boolean.FALSE);
            orderRepository.save(order);
        }

        mav.addObject("orders", orderRepository.findAllWithDistribution());

        return mav;
    }

}