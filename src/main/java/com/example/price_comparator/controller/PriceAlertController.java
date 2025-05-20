package com.example.price_comparator.controller;

import com.example.price_comparator.model.PriceAlert;
import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alerts")
public class PriceAlertController {

    private final PriceService priceService;

    @GetMapping("/check")
    public List<PriceEntry> checkAlert(@RequestParam String productName, @RequestParam double targetPrice) {
        PriceAlert alert = new PriceAlert(productName, targetPrice, "dummy@example.com");
        return priceService.checkAlerts(alert);
    }

    @PostMapping
    public String addAlert(@RequestBody PriceAlert alert) {
        priceService.addAlert(alert);
        return "Price alert added successfully!";
    }

}
