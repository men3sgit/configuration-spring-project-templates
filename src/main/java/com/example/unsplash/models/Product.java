package com.example.unsplash.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            initialValue = 3,
            allocationSize = 2
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "product_sequence"
    )
    private Long id;
    @Column(
            nullable = false
    )
    private String name;
    private String description;
    @Column(
            nullable = false
    )
    private double price;
    @Column(name = "created_at")
    private LocalDate createdAt;
    public Product(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
        createdAt = LocalDate.now();
    }
    @PrePersist
    public void prePersist() {
        createdAt = LocalDate.now();
    }
}

