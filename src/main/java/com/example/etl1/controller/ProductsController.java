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
    public String populateProductData() {
        int count = 10;

        for (int i = 1; i <= count; i++) {
            var color = firstOrNull(colorRepository.findAll());
            var casePart = firstOrNull(caseRepository.findAll());
            var cpu = firstOrNull(cpuRepository.findAll());
            var cooler = firstOrNull(cpuCoolerRepository.findAll());
            var gpu = firstOrNull(graphicsCardRepository.findAll());
            var storage = firstOrNull(internalStorageRepository.findAll());
            var memory = firstOrNull(memoryRepository.findAll());
            var mobo = firstOrNull(motherboardRepository.findAll());
            var psu = firstOrNull(powerSupplyRepository.findAll());

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
            product.setName(productNames.get(i - 1));
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
        return "redirect:/";
    }

    private <T> T firstOrNull(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .findFirst()
                .orElse(null);
    }

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
