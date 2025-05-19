package com.example.price_comparator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceEntry {
    private String store;
    private LocalDate date;
    private Product product;
    private double price;
    private String currency;

    public double getValuePerUnit(){
        double quantity = product.getQuantity();
        if (quantity == 0) return Double.MAX_VALUE; // Avoid division by zero
        return price/quantity;
    }
}