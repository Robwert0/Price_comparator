package com.example.price_comparator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BasketItems {
    private  String productName;
    private double quantity;
    private String unit;
}
