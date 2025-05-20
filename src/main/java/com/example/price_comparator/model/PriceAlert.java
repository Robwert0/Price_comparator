package com.example.price_comparator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceAlert {
    private String productName;
    private double targetPrice;
    private String userEmail;
}
