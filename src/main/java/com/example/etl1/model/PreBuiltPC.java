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
    private String origin;
    private String gpu;
    private String cpu;
    private String memory;
    private String ssd;
    private LocalDateTime scrapeDate;
    @Column(unique = true)
    private String modelNumber;

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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getGpu() { return gpu; }

    public void setGpu(String gpu) { this.gpu = gpu; };

    public String getCpu() { return cpu; }

    public void setCpu(String cpu) { this.cpu = cpu; }

    public String getMemory() { return memory; }

    public void setMemory(String memory) { this.memory = memory; }

    public String getSsd() { return ssd; }

    public void setSsd(String ssd) { this.ssd = ssd; }

    public LocalDateTime getScrapeDate() {
        return scrapeDate;
    }

    public void setScrapeDate(LocalDateTime scrapeDate) {
        this.scrapeDate = scrapeDate;
    }

    public String getModelNumber() { return modelNumber; }

    public void setModelNumber(String modelNumber) { this.modelNumber = modelNumber; }

    @Override
    public String toString() {
        return "PreBuiltPC{" +
                "id=" + id +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", origin=" + origin + '\'' +
                ", gpu=" + gpu + '\'' +
                ", cpu=" + cpu + '\'' +
                ", memory=" + memory + '\'' +
                ", ssd=" + ssd + '\'' +
                ", scrapeDate=" + scrapeDate + '\'' +
                ", modelNumber=" + modelNumber +
                '}';
    }
}
