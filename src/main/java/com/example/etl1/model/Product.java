package com.example.etl1.model;
import com.example.etl1.model.components.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "is_custom", nullable = false)
    private Boolean isCustom = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", referencedColumnName = "id")
    private Case caseEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpu_id", referencedColumnName = "id")
    private Cpu cpu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpu_cooler_id", referencedColumnName = "id")
    private CpuCooler cpuCooler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "graphics_card_id", referencedColumnName = "id")
    private GraphicsCard graphicsCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internal_storage_id", referencedColumnName = "id")
    private InternalStorage internalStorage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memory_id", referencedColumnName = "id")
    private Memory memory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motherboard_id", referencedColumnName = "id")
    private Motherboard motherboard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "power_supply_id", referencedColumnName = "id")
    private PowerSupply powerSupply;

}