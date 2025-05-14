package com.example.price_comparator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String id;
    private String name;
    private String category;
    private String brand;
    private double quantity;  // Ex: 1, 500
    private String unit;      // Ex: litru, gram, bucata
}