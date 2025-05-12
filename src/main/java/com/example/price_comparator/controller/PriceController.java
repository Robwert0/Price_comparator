package com.example.price_comparator.controller;

import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping
    public List<PriceEntry> getAllPrices() {
        return priceService.getAllPrices();
    }
}
