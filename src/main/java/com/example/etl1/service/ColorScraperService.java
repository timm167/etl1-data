package com.example.etl1.service;

import com.example.etl1.entity.Color;
import com.example.etl1.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ColorScraperService {

    private final ColorRepository colorRepository;

    @Value("${scraper.delay-seconds:60}")
    private int delaySeconds;

    public String scrapeColors() {
        try {
            log.info("Starting color scraping from MyPerfectColor.com");

            String url = "https://www.myperfectcolor.com/Paint-Colors-By-Hue/3220.htm";


            Connection connection = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Connection", "keep-alive")
                    .header("Upgrade-Insecure-Requests", "1")
                    .referrer("https://www.google.com/")
                    .timeout(30000)
                    .followRedirects(true)
                    .ignoreHttpErrors(false);

            Connection.Response response = connection.execute();

            int statusCode = response.statusCode();
            if (statusCode != 200) {
                log.error("Failed to fetch page. Status={} Headers={}", statusCode, response.headers());
                return "Scraping failed: HTTP error fetching URL. Status=" + statusCode + ", URL=[" + url + "]";
            }

            Document doc = response.parse();

            List<Color> newColors = new ArrayList<>();


            Elements colorElements = doc.select(".color-item, .color-box, .swatch, [class*=color]");

            log.info("Found {} potential color elements", colorElements.size());

            for (Element element : colorElements) {
                try {
                    String colorName = extractColorName(element);
                    String hexCode = extractHexCode(element);

                    if (isValidColor(colorName, hexCode)) {
                        // see if color is already in the database
                        if (!colorRepository.existsByName(colorName)) {
                            Color color = new Color();
                            color.setName(colorName);
                            color.setHexCode(hexCode);
                            newColors.add(color);

                            log.debug("Found new color: {} - {}", colorName, hexCode);
                        }
                    }
                } catch (Exception e) {
                    log.debug("Error processing element: {}", e.getMessage());
                }
            }

            // save
            colorRepository.saveAll(newColors);


            Thread.sleep(delaySeconds * 1000);

            String result = String.format("Scraping completed. Found %d new colors.", newColors.size());
            log.info(result);
            return result;

        } catch (Exception e) {
            String error = "Scraping failed: " + e.getMessage();
            log.error(error, e);
            return error;
        }
    }


    private String extractColorName(Element element) {

        String name = element.attr("title");
        if (name.isEmpty()) name = element.attr("alt");
        if (name.isEmpty()) name = element.text().trim();
        if (name.isEmpty()) name = element.attr("data-color");

        return cleanString(name);
    }

    private String extractHexCode(Element element) {

        String style = element.attr("style");
        if (style.contains("#")) {
            return extractHexFromText(style);
        }

        String dataColor = element.attr("data-hex");
        if (!dataColor.isEmpty()) {
            return normalizeHex(dataColor);
        }


        String text = element.text();
        return extractHexFromText(text);
    }

    private String extractHexFromText(String text) {
        if (text == null) return null;


        java.util.regex.Pattern hexPattern = java.util.regex.Pattern.compile("#[0-9A-Fa-f]{6}");
        java.util.regex.Matcher matcher = hexPattern.matcher(text);

        if (matcher.find()) {
            return matcher.group().toUpperCase();
        }

        return null;
    }

    private String normalizeHex(String hex) {
        if (hex == null || hex.isEmpty()) return null;

        hex = hex.trim().toUpperCase();
        if (!hex.startsWith("#")) {
            hex = "#" + hex;
        }

        return hex.matches("#[0-9A-F]{6}") ? hex : null;
    }

    private String cleanString(String str) {
        if (str == null) return null;
        return str.trim().replaceAll("\\s+", " ");
    }

    private boolean isValidColor(String name, String hexCode) {
        return name != null && !name.isEmpty() &&
                hexCode != null && hexCode.matches("#[0-9A-F]{6}");
    }
}