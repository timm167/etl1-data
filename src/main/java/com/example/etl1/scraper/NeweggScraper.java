package com.example.etl1.scraper;
import com.example.etl1.model.PreBuiltPC;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NeweggScraper {
    private static final String newegg_Url = "https://www.newegg.com/global/uk-en/p/pl?d=prebuilt&PageSize=96";
    private static final String user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36";

    public List<PreBuiltPC> scrapePrebuiltPCs() throws IOException {
        Document doc = Jsoup.connect(newegg_Url)
                .userAgent(user_agent)
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Referer", "https://www.google.com")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .get();

        Elements productContainers = doc.select("div.item-cells-wrap > div.item-cell");
        List<PreBuiltPC> pcList = new ArrayList<>();

        for (Element container : productContainers) {
            Element nameElement = container.selectFirst("a.item-title");
            Element priceElement = container.selectFirst("li.price-current");
            Elements featureElements = container.select("ul.item-features li");

            if (nameElement != null && priceElement != null) {
                String name = nameElement.text().trim();
                String[] nameParts = name.split("PC");
                if (nameParts.length > 0) {
                    name = nameParts[0].trim() + " PC";
                }
                String price = "";
                price = priceElement.select("strong").text() + priceElement.select("sup").text();
                price = price.replace("£", "").replace(",", "").trim();
                List<String> features = featureElements.stream()
                        .map(Element::text)
                        .collect(Collectors.toList());

                PreBuiltPC pc = new PreBuiltPC();
                pc.setName(name);
                pc.setPrice(price);
                pc.setFeatures(features);
                pc.setOrigin("Newegg");
                pc.setScrapeDate(LocalDateTime.now());
                pcList.add(pc);
            }

        }
        return pcList;
    }
}