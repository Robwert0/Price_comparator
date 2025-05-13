package com.example.price_comparator.controller;

import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.service.NewDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class newDiscountController {

    @Autowired
    private NewDiscountService newDiscountService;

    @GetMapping("/new")
    public List<PriceEntry> getNewDiscounts(){
        LocalDate today = LocalDate.of(2025, 5, 8);
        LocalDate yesterday = LocalDate.of(2025, 5, 7);

        return newDiscountService.getNewDiscounts(today, yesterday);
    }
}
