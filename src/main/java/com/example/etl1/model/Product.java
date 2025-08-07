package com.example.etl1.model;
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

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "color_id")
    private Integer colorId;

    @Column(name = "case_id")
    private Integer caseId;

    @Column(name = "cpu_id")
    private Integer cpuId;

    @Column(name = "cpu_cooler_id")
    private Integer cpuCoolerId;

    @Column(name = "graphics_card_id")
    private Integer graphicsCardId;

    @Column(name = "internal_storage_id")
    private Integer internalStorageId;

    @Column(name = "memory_id")
    private Integer memoryId;

    @Column(name = "motherboard_id")
    private Integer motherboardId;

    @Column(name = "power_supply_id")
    private Integer powerSupplyId;
}