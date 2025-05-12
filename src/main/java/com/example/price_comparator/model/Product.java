package com.example.price_comparator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private String brand;
    private String category;
    private double grammage;  // Ex: 1, 500
    private String unit;      // Ex: litru, gram, bucata
}