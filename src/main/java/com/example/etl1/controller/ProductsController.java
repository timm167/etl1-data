package com.example.etl1.controller;

import com.example.etl1.dto.BasketResponseDto;
import com.example.etl1.model.ComponentIdCarrier;
import com.example.etl1.model.Product;
import com.example.etl1.repository.*;
import com.example.etl1.service.BasketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProductsController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BasketService basketService;

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

    @GetMapping("/products/create")
    public ModelAndView createCustomPc(HttpSession session, @ModelAttribute("componentIds") ComponentIdCarrier componentIds) {
        ModelAndView modelAndView = new ModelAndView("/custom-pc");

        if (session.getAttribute("componentIds") == null) {
            session.setAttribute("componentIds", new ComponentIdCarrier());
        }

        if (session.getAttribute("customPc") == null) {
            session.setAttribute("customPc", new Product());
        }

        modelAndView.addObject("customPc", session.getAttribute("customPc"));
        modelAndView.addObject("componentIds", session.getAttribute("componentIds"));
        modelAndView.addObject("name", "");

        return modelAndView;
    }

    @PostMapping("/products")
    public String postCustomPc(HttpSession session, String productName) {
        Product customPc = (Product) session.getAttribute("customPc");

        BigDecimal cost = BigDecimal.ZERO;
        if (customPc.getCaseEntity() != null) cost = cost.add(BigDecimal.valueOf(customPc.getCaseEntity().getPrice()));
        if (customPc.getCpu() != null) cost = cost.add(BigDecimal.valueOf(customPc.getCpu().getPrice()));
        if (customPc.getCpuCooler() != null) cost = cost.add(BigDecimal.valueOf(customPc.getCpuCooler().getPrice()));
        if (customPc.getGraphicsCard() != null) cost = cost.add(BigDecimal.valueOf(customPc.getGraphicsCard().getPrice()));
        if (customPc.getInternalStorage() != null) cost = cost.add(BigDecimal.valueOf(customPc.getInternalStorage().getPrice()));
        if (customPc.getMemory() != null) cost = cost.add(BigDecimal.valueOf(customPc.getMemory().getPrice()));
        if (customPc.getMotherboard() != null) cost = cost.add(BigDecimal.valueOf(customPc.getMotherboard().getPrice()));
        if (customPc.getPowerSupply() != null) cost = cost.add(BigDecimal.valueOf(customPc.getPowerSupply().getPrice()));

        BigDecimal price = cost.multiply(BigDecimal.valueOf(1.5));
        customPc.setCost(cost);
        customPc.setPrice(price);
        customPc.setIsCustom(true);
        customPc.setName(productName);

        productRepository.save(customPc);

        session.setAttribute("customPc", null);
        session.setAttribute("componentIds", null);

        return "redirect:/";
    }

    // BASKET FUNCTIONALITY
    @GetMapping("/basket")
    public String viewBasket(Model model, HttpSession session) {
        try {
            String sessionId = session.getId();
            BasketResponseDto basket = basketService.getBasket(sessionId);

            model.addAttribute("basket", basket);
            model.addAttribute("basketItems", basket.getItems());
            model.addAttribute("subtotal", basket.getTotalPrice());
            model.addAttribute("itemCount", basket.getTotalItems());

            // Calculate tax and shipping
            BigDecimal tax = basket.getTotalPrice().multiply(BigDecimal.valueOf(0.20)); // 20% VAT
            BigDecimal shipping = basket.getItems().isEmpty() ?
                    BigDecimal.ZERO : BigDecimal.valueOf(5.99);
            BigDecimal total = basket.getTotalPrice().add(tax).add(shipping);

            model.addAttribute("tax", tax);
            model.addAttribute("shipping", shipping);
            model.addAttribute("total", total);

            return "basket";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Unable to load basket: " + e.getMessage());
            return "basket";
        }
    }

    // Add to basket endpoint
    @PostMapping("/basket/add/{productId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addToBasket(
            @PathVariable Integer productId,
            @RequestParam(defaultValue = "1") Integer quantity,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            String sessionId = session.getId();
            BasketResponseDto basket = basketService.addItemToBasket(sessionId, productId, quantity);

            response.put("success", true);
            response.put("message", "Item added to basket successfully");
            response.put("basketCount", basket.getTotalItems());
            response.put("basketTotal", basket.getTotalPrice());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to add item: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Update quantity endpoint
    @PostMapping("/basket/update/{productId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateQuantity(
            @PathVariable Integer productId,
            @RequestParam Integer quantity,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            String sessionId = session.getId();
            BasketResponseDto basket = basketService.updateItemQuantity(sessionId, productId, quantity);

            response.put("success", true);
            response.put("message", "Quantity updated successfully");
            response.put("basketCount", basket.getTotalItems());
            response.put("basketTotal", basket.getTotalPrice());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to update quantity: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Remove item endpoint
    @DeleteMapping("/basket/remove/{productId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> removeFromBasket(
            @PathVariable Integer productId,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            String sessionId = session.getId();
            BasketResponseDto basket = basketService.removeItemFromBasket(sessionId, productId);

            response.put("success", true);
            response.put("message", "Item removed from basket");
            response.put("basketCount", basket.getTotalItems());
            response.put("basketTotal", basket.getTotalPrice());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to remove item: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Get basket count for navbar
    @GetMapping("/basket/count")
    @ResponseBody
    public ResponseEntity<Integer> getBasketCount(HttpSession session) {
        try {
            String sessionId = session.getId();
            BasketResponseDto basket = basketService.getBasket(sessionId);
            return ResponseEntity.ok(basket.getTotalItems());
        } catch (Exception e) {
            return ResponseEntity.ok(0);
        }
    }
}