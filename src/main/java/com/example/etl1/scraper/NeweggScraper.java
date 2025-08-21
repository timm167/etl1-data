package com.example.etl1.scraper;
import com.example.etl1.model.PreBuiltPC;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class NeweggScraper {
    private static final String newegg_Url = "https://www.newegg.com/global/uk-en/p/pl?d=prebuilt&PageSize=96&page=%d";
    private static final List<String> user_agents = Arrays.asList(
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:107.0) Gecko/20100101 Firefox/107.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:124.0) Gecko/20100101 Firefox/124.0",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 Edg/123.0.2420.81",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 OPR/109.0.0.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 14.4; rv:124.0) Gecko/20100101 Firefox/124.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.4.1 Safari/605.1.15",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 OPR/109.0.0.0",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36",
            "Mozilla/5.0 (X11; Linux i686; rv:124.0) Gecko/20100101 Firefox/124.0"
    );
    private final Random random = new Random();

    private Optional<String> getFeature(Elements features, String label) {
        for (Element feature : features) {
            Element strongTag = feature.selectFirst("strong");
            if (strongTag != null && strongTag.text().equals(label)) {
                return Optional.of(feature.ownText().trim());
            }
        }
        return Optional.empty();
    }

    public List<PreBuiltPC> scrapeAll() throws IOException {
        List<PreBuiltPC> allPcs = new ArrayList<>();
        allPcs.addAll(scrapePage(1));
        allPcs.addAll(scrapePage(2));
        return allPcs;
    }

    public List<PreBuiltPC> scrapePage(int page) throws IOException {
        String url = String.format(newegg_Url, page);
        String randomUserAgent = user_agents.get(random.nextInt(user_agents.size()));

        Document doc = Jsoup.connect(url)
                .userAgent(randomUserAgent)
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

            Optional<String> gpuOptional = getFeature(featureElements, "GPU/VGA Type:");
            Optional<String> cpuOptional = getFeature(featureElements, "CPU Name:");
            Optional<String> memoryOptional = getFeature(featureElements, "Memory Capacity:");
            Optional<String> ssdOptional = getFeature(featureElements, "SSD:");

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
                String modelNumber = features.stream()
                        .filter(f -> f.startsWith("Model #: "))
                        .map(f -> f.replace("Model #: ", "").trim())
                        .findFirst()
                        .orElse(null);

                if (gpuOptional.isPresent() && cpuOptional.isPresent() && memoryOptional.isPresent() && ssdOptional.isPresent()) {
                    PreBuiltPC pc = new PreBuiltPC();
                    pc.setName(name);
                    pc.setPrice(price);
                    pc.setOrigin("Newegg");
                    pc.setScrapeDate(LocalDateTime.now());
                    pc.setModelNumber(modelNumber);
                    pc.setGpu(gpuOptional.get());
                    pc.setCpu(cpuOptional.get());
                    pc.setMemory(memoryOptional.get());
                    pc.setSsd(ssdOptional.get());
                    pcList.add(pc);
                }
            }

        }
        return pcList;
    }
}