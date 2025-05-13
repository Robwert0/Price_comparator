package com.example.price_comparator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiscountedProduct {
    private Product product;
    private String storeName;
    private double originalPrice;
    private double currentPrice;
    private double discountedPercentage;
}
