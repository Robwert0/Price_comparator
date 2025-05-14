package com.example.price_comparator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DiscountedProduct {
    private Product product;
    private String storeName;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int percentageOfDiscount;
}
