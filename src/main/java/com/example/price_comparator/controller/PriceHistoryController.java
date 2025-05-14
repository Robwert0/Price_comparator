package com.example.price_comparator.controller;

import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.service.PriceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class PriceHistoryController {

    @Autowired
    private PriceHistoryService priceHistoryService;

    @GetMapping
    public List<PriceEntry> getPriceHistory(
            @RequestParam String productName,
            @RequestParam(required = false) String store,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category
    ){
        return priceHistoryService.getPriceHistory(productName, store, brand, category);
    }
}
