package com.example.etl1.service;
import com.example.etl1.model.PreBuiltPC;
import com.example.etl1.Repositories.PrebuiltPCRepository;
import com.example.etl1.scraper.NeweggScraper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@Service
public class Scraping {
    private final NeweggScraper scraper = new NeweggScraper();
    private final PrebuiltPCRepository prebuiltPCRepository;

    @Autowired
    public Scraping(PrebuiltPCRepository prebuiltPCRepository) {
        this.prebuiltPCRepository = prebuiltPCRepository;
    }

    public void runNeweggProcess() {
        try {
            List<PreBuiltPC> pcs = scraper.scrapePrebuiltPCs();

            if (pcs.isEmpty()) {
                System.out.println("No data found");
            } else {
                prebuiltPCRepository.saveAll(pcs);
                System.out.println("Saved " + pcs.size() + " PCs to the database");
            }
        } catch (IOException e) {
            System.err.println("Failed to run: " + e.getMessage());
            e.printStackTrace();
        }
    }
}