package com.example.etl1.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal value;

    @Column(name = "distribution_channel_id", nullable = false)
    private Integer distributionChannelId;

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @Column(name = "expected_delivery_time")
    private LocalDateTime expectedDeliveryTime;

    @Column(name = "is_open")
    private Boolean isOpen = true;
}
