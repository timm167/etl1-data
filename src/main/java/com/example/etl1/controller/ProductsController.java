package com.example.etl1.controller;

import com.example.etl1.model.ComponentIdCarrier;
import com.example.etl1.model.Product;
import com.example.etl1.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.math.BigDecimal;

@Controller
public class ProductsController {

    @Autowired
    ProductRepository productRepository;  // Now works with model.Product

    @Autowired
    ReviewRepository reviewRepository;

    @GetMapping("/products")
    public ModelAndView viewProducts(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/products");

        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            modelAndView.addObject("products", productRepository.findAll(sort));
        } else {
            modelAndView.addObject("products", productRepository.findAll());
        }


        Sort reviewSort = Sort.by(Sort.Direction.DESC, "createdAt");
        java.util.Map<Integer, java.util.List<com.example.etl1.model.Review>> reviewsByProductId = new java.util.HashMap<>();

        Iterable<Product> productsForReviews;

        if (sort != null) {
            productsForReviews = productRepository.findAll(sort);
        } else {
            productsForReviews = productRepository.findAll();
        }

        for (Product p : productsForReviews) {
            reviewsByProductId.put(p.getId(), reviewRepository.findByProductId(p.getId(), reviewSort));
        }
        modelAndView.addObject("reviewsByProductId", reviewsByProductId);


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
        if (customPc.getMotherboard() != null) cost = cost.add(BigDecimal.valueOf(customPc.getMotherboard() .getPrice()));
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
  
    @GetMapping("/basket")
      public ModelAndView viewBasket() {
          ModelAndView modelAndView = new ModelAndView("/basket");
          return modelAndView;
    }
}
