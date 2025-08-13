package com.example.etl1.controller;

import com.example.etl1.model.ComponentIdCarrier;
import com.example.etl1.model.Product;
import com.example.etl1.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
public class ProductsController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public ModelAndView viewProducts(String sortBy, String order, String filterBy, String filterOp, Double numberFilter) {
        ModelAndView modelAndView = new ModelAndView("/products");
        Sort sort = DataSortHelper.getSortMethod(sortBy, order);

        if (sort != null) {
            List<Product> sortedProducts = productRepository.findAll(sort);
            List<Product> filteredProducts = getFilteredProducts(sortedProducts, filterBy, filterOp, numberFilter);

            modelAndView.addObject("products", filteredProducts);
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

    private List<Product> getFilteredProducts(List<Product> products, String filterBy, String filterOp, Double numberFilter) {
        Predicate<Double> compareForFilter;

        if (filterOp.equals("at least")) {
            compareForFilter = value -> value >= numberFilter;
        } else {
            compareForFilter = value -> value <= numberFilter;
        }

        Predicate<Product> filterFunction;

        switch (filterBy) {
            case "Price":
                filterFunction = product -> compareForFilter.test(product.getPrice().doubleValue());
                break;
            case "CPU clock speed":
                filterFunction = product -> compareForFilter.test(product.getCpu().getCoreClock());
                break;
            case "GPU clock speed":
                filterFunction = product -> compareForFilter.test((double) product.getGraphicsCard().getCoreClock());
                break;
            default:
                return products;
        }

        return products.stream().filter(filterFunction).collect(Collectors.toCollection(ArrayList::new));
    }
}
