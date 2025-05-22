package com.example.price_comparator.controller;

import com.example.price_comparator.model.DiscountedProduct;
import com.example.price_comparator.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping("/best")
    public ResponseEntity<List<DiscountedProduct>> getBestDscounts(){
        LocalDate date = LocalDate.of(2025, 5, 2);
        return ResponseEntity.ok(discountService.getBestDiscountPerProduct(date));
    }
}
