package com.example.etl1.model;

import java.util.List;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

@Entity
@Table(name = "price_comparison")
public class PreBuiltPC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String price;
    @Convert(converter = StringListConverter.class)
    private List<String> features;
    private String origin;
    private LocalDateTime scrapeDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public List<String> getFeatures() {
        return features;
    }
    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public LocalDateTime getScrapeDate() {
        return scrapeDate;
    }

    public void setScrapeDate(LocalDateTime scrapeDate) {
        this.scrapeDate = scrapeDate;
    }

    @Override
    public String toString() {
        return "PreBuiltPC{" +
                "id=" + id +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", features=" + features + '\'' +
                ", origin=" + origin + '\'' +
                ", scrapeDate=" + scrapeDate +
                '}';
    }
}
