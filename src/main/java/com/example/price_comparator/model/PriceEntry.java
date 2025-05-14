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
}