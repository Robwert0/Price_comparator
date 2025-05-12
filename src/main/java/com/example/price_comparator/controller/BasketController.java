package com.example.price_comparator.controller;

import com.example.price_comparator.model.BasketItems;
import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.model.Product;
import com.example.price_comparator.service.BasketOptimizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/basket")
public class BasketController {
    @Autowired
    private BasketOptimizerService optimizerService;

    @PostMapping("/optimize")
    public Map<String, PriceEntry> optimizeBasket(@RequestBody List<BasketItems> items) {
        return optimizerService.optimizeBasket(items, LocalDate.of(2025, 5, 8));
    }
}
