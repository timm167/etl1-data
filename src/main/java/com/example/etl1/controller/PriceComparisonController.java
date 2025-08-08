package com.example.etl1.controller;
import com.example.etl1.model.PreBuiltPC;
import com.example.etl1.repository.PrebuiltPCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class PriceComparisonController {

    @Autowired
    private PrebuiltPCRepository prebuiltPCRepository;

    @GetMapping("/pricecomparison")
    public String showPCs(Model model) {
        List<PreBuiltPC> pcs = prebuiltPCRepository.findAll();
        model.addAttribute("pcs", pcs);
        return "newegg-pcs";
    }
}
