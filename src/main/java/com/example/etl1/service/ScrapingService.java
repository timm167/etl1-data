package com.example.etl1.service;
import com.example.etl1.model.PreBuiltPC;
import com.example.etl1.repository.PrebuiltPCRepository;
import com.example.etl1.scraper.NeweggScraper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ScrapingService {
    private final NeweggScraper scraper = new NeweggScraper();
    private final PrebuiltPCRepository prebuiltPCRepository;

    @Autowired
    public ScrapingService(PrebuiltPCRepository prebuiltPCRepository) {
        this.prebuiltPCRepository = prebuiltPCRepository;
    }

    public void runNeweggProcess() {
        try {
            List<PreBuiltPC> pcs = scraper.scrapePrebuiltPCs();

            if (pcs.isEmpty()) {
                System.out.println("No data found");
            } else {
                for (PreBuiltPC pc : pcs) {
                    Optional<PreBuiltPC> existingPcOptional = prebuiltPCRepository.findByModelNumber(pc.getModelNumber());
                    if (existingPcOptional.isPresent()) {
                        PreBuiltPC existingPc = existingPcOptional.get();
                        existingPc.setPrice(pc.getPrice());
                        existingPc.setScrapeDate((pc.getScrapeDate()));
                        prebuiltPCRepository.save(existingPc);
                        System.out.println("Updated existing PC: " + existingPc.getName());
                    } else {
                        prebuiltPCRepository.save(pc);
                        System.out.println("Saved new PC: " + pc.getName());
                }
            }

            }
        } catch (IOException e) {
            System.err.println("Failed to run: " + e.getMessage());
            e.printStackTrace();
        }
    }
}