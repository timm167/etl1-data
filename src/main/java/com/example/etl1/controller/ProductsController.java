package com.example.etl1.controller;

import com.example.etl1.model.Case;
import com.example.etl1.model.Product;
import com.example.etl1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RestController
public class ProductsController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    CaseRepository caseRepository;

    @Autowired
    CpuRepository cpuRepository;

    @Autowired
    CpuCoolerRepository cpuCoolerRepository;

    @Autowired
    GraphicsCardRepository graphicsCardRepository;

    @Autowired
    InternalStorageRepository internalStorageRepository;

    @Autowired
    MemoryRepository memoryRepository;

    @Autowired
    MotherboardRepository motherboardRepository;

    @Autowired
    PowerSupplyRepository powerSupplyRepository;


    @GetMapping("/load-product-data")
    public String populateProductData() throws IOException {
        Integer count = 10;

        for (int i = 1; i <= count; i++) {
            var color = colorRepository.findById(i).orElseThrow();
            var casePart = caseRepository.findById(i).orElseThrow();
            var cpu = cpuRepository.findById(i).orElseThrow();
            var cooler = cpuCoolerRepository.findById(i).orElseThrow();
            var gpu = graphicsCardRepository.findById(i).orElseThrow();
            var storage = internalStorageRepository.findById(i).orElseThrow();
            var memory = memoryRepository.findById(i).orElseThrow();
            var mobo = motherboardRepository.findById(i).orElseThrow();
            var psu = powerSupplyRepository.findById(i).orElseThrow();

            BigDecimal cost = BigDecimal.ZERO;

            if (casePart != null) cost = cost.add(BigDecimal.valueOf(casePart.getPrice()));
            if (cpu != null) cost = cost.add(BigDecimal.valueOf(cpu.getPrice()));
            if (cooler != null) cost = cost.add(BigDecimal.valueOf(cooler.getPrice()));
            if (gpu != null) cost = cost.add(BigDecimal.valueOf(gpu.getPrice()));
            if (storage != null) cost = cost.add(BigDecimal.valueOf(storage.getPrice()));
            if (memory != null) cost = cost.add(BigDecimal.valueOf(memory.getPrice()));
            if (mobo != null) cost = cost.add(BigDecimal.valueOf(mobo.getPrice()));
            if (psu != null) cost = cost.add(BigDecimal.valueOf(psu.getPrice()));


            BigDecimal price = cost.multiply(BigDecimal.valueOf(1.5));

            List<String> productNames = Arrays.asList(
                    "Carboniser 2000 CPU",
                    "TurboMax Desktop",
                    "NeonWave CPU",
                    "PixelPro CPU",
                    "VoltEdge Desktop",
                    "CosmoTech CPU",
                    "ByteStream Tower",
                    "QuantumFlex CPU",
                    "HyperCore Desktop",
                    "MegaSync CPU"
            );

            Product product = new Product();
            product.setName(productNames.get(i-1));
            product.setCost(cost);
            product.setPrice(price);
            product.setColor(color);
            product.setCaseEntity(casePart);
            product.setCpu(cpu);
            product.setCpuCooler(cooler);
            product.setGraphicsCard(gpu);
            product.setInternalStorage(storage);
            product.setMemory(memory);
            product.setMotherboard(mobo);
            product.setPowerSupply(psu);
            productRepository.save(product);
        }
        return "redirect:/index";

    }

    @GetMapping("/products")
    public ModelAndView viewCases() {
        ModelAndView modelAndView = new ModelAndView("/products");
        modelAndView.addObject("products", productRepository.findAll());
        modelAndView.addObject("sortBy", "");
        modelAndView.addObject("order", "");
        return modelAndView;
    }

    @GetMapping("/products/sort")
    public ModelAndView viewSortedCases(String sortBy, String order) {
        ModelAndView modelAndView = new ModelAndView("/products");

        Sort.Direction direction = null;

        if (order.equals("Descending")) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        String property = switch (sortBy) {
            case "Name" -> "name";
            case "Price" -> "price";
            default -> null;
        };

        List<Product> product = productRepository.findAll(Sort.by(direction, property));
        modelAndView.addObject("products", product);
        return modelAndView;
    }

}
