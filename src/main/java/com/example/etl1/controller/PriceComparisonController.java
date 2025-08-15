package com.example.etl1.controller;
import com.example.etl1.model.PreBuiltPC;
import com.example.etl1.model.components.Cpu;
import com.example.etl1.model.components.GraphicsCard;
import com.example.etl1.model.components.Memory;
import com.example.etl1.model.components.InternalStorage;
import com.example.etl1.repository.PrebuiltPCRepository;
import com.example.etl1.repository.components.CpuRepository;
import com.example.etl1.repository.components.GraphicsCardRepository;
import com.example.etl1.repository.components.MemoryRepository;
import com.example.etl1.repository.components.InternalStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class PriceComparisonController {

    @Autowired
    private PrebuiltPCRepository prebuiltPCRepository;

    @Autowired
    private CpuRepository cpuRepository;

    @Autowired
    private GraphicsCardRepository graphicsCardRepository;

    @Autowired
    private MemoryRepository memoryRepository;

    @Autowired
    private InternalStorageRepository internalStorageRepository;

    @GetMapping("/pricecomparison")
    public String showPCs(@RequestParam(value = "search", required = false) String searchTerm, Model model) {
        List<PreBuiltPC> pcs;

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            pcs = prebuiltPCRepository.findByComponentNames(searchTerm);
        } else {
            pcs = prebuiltPCRepository.findAll();
        }
        List<Map<String, Object>> pcComparisonData = new ArrayList<>();

        for (PreBuiltPC pc : pcs) {
            Map<String, Object> pcData = new HashMap<>();
            double totalComponentPrice = 0.0;

            pcData.put("pcName", pc.getName());
            pcData.put("neweggPrice", Double.parseDouble(pc.getPrice()));

            Optional<Cpu> cpuOptional = cpuRepository.findByNameContaining(pc.getCpu()).stream().findFirst();
            if (cpuOptional.isPresent()) {
                pcData.put("cpuName", pc.getCpu());
                pcData.put("cpuPrice", cpuOptional.get().getPrice());
                totalComponentPrice += cpuOptional.get().getPrice();
            } else {
                pcData.put("cpuName", "N/A");
                pcData.put("cpuPrice", 0.0);
            }

            Optional<GraphicsCard> graphicsCardOptional = graphicsCardRepository.findByChipsetContaining(pc.getGpu()).stream().findFirst();
            if (graphicsCardOptional.isPresent()) {
                pcData.put("graphicsCardName", pc.getGpu());
                pcData.put("graphicsCardPrice", graphicsCardOptional.get().getPrice());
                totalComponentPrice += graphicsCardOptional.get().getPrice();
            } else {
                pcData.put("graphicsCardName", "N/A");
                pcData.put("graphicsCardPrice", 0.0);
            }

            String scrapedMemory = pc.getMemory();
            String[] words = scrapedMemory.split("\\s+");
            Optional<Memory> memoryOptional = Optional.empty();

            List<String> searchTerms = new ArrayList<>();
            for (String word : words) {
                if (word.endsWith("GB")) {
                    searchTerms.add(word);
                    String capacity = word.substring(0, word.length() - 2);
                    String unit = word.substring(word.length() - 2);
                    searchTerms.add(capacity + " " + unit);
                }
            }

            for (String term : searchTerms) {
                List<Memory> foundMemoryList = memoryRepository.findByNameContaining(term);
                for (Memory foundMemory : foundMemoryList) {
                    if (foundMemory.getPrice() > 0.0) {
                        memoryOptional = Optional.of(foundMemory);
                        break;
                    }
                }
                if (memoryOptional.isPresent()) {
                    break;
                }
            }
            if (memoryOptional.isPresent()) {
                pcData.put("memoryName", scrapedMemory);
                pcData.put("memoryPrice", memoryOptional.get().getPrice());
                totalComponentPrice += memoryOptional.get().getPrice();
            } else {
                pcData.put("memoryName", "N/A");
                pcData.put("memoryPrice", 0.0);
            }

            String scrapedSsd = pc.getSsd();
            Pattern pattern = Pattern.compile("(\\d+)\\s*(TB)");
            Matcher matcher = pattern.matcher(scrapedSsd);
            Optional<InternalStorage> internalStorageOptional = Optional.empty();

            if (matcher.find()) {
                int capacityValue = Integer.parseInt(matcher.group(1));
                String unit = matcher.group(2).toUpperCase();
                if (unit.equals("TB")) {
                    capacityValue *= 1000;
                }
                List<InternalStorage> foundStorageList = internalStorageRepository.findByCapacity(capacityValue);
                for (InternalStorage foundStorage : foundStorageList) {
                    if (foundStorage.getPrice() > 0.0) {
                        internalStorageOptional = Optional.of(foundStorage);
                        break;
                    }
                }
            }
            if (internalStorageOptional.isPresent()) {
                pcData.put("internalStorageName", scrapedSsd);
                pcData.put("internalStoragePrice", internalStorageOptional.get().getPrice());
                totalComponentPrice += internalStorageOptional.get().getPrice();
            } else {
                pcData.put("internalStorageName", "N/A");
                pcData.put("internalStoragePrice", 0.0);
            }

            String formattedTotal = String.format("%.2f", totalComponentPrice);
            pcData.put("totalComponentPrice", formattedTotal);
            pcComparisonData.add(pcData);
        }
        model.addAttribute("pcComparisonData", pcComparisonData);
        return "newegg-pcs";
    }
}
