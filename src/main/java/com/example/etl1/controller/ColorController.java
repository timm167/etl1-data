package com.example.etl1.controller;

import com.example.etl1.entity.Color;
import com.example.etl1.repository.ColorRepository;
import com.example.etl1.service.ColorScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ColorController {

    private final ColorScraperService scraperService;
    private final ColorRepository colorRepository;

    @GetMapping("/scrape")
    @ResponseBody
    public String scrapeColors() {
        String result = scraperService.scrapeColors();
        System.out.println("[DEBUG] Scrape result: " + result);
        return result;
    }


    @GetMapping("/colors")
    public String showColors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("name"));
        var colorsPage = colorRepository.findAll(pageRequest);

        model.addAttribute("colors", colorsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", colorsPage.getTotalPages());
        model.addAttribute("totalColors", colorsPage.getTotalElements());

        return "colors";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/colors";
    }
}