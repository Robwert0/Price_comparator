package com.example.price_comparator.controller;

import com.example.price_comparator.model.PriceAlert;
import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.service.PriceService;
import com.example.price_comparator.util.PriceAlertScheduler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alerts")
public class PriceAlertController {

    private final PriceService priceService;
    private final PriceAlertScheduler priceAlertScheduler;

    @GetMapping("/check")
    public List<PriceEntry> checkAlert(@RequestBody PriceAlert alert) {
        return priceService.checkAlerts(alert);
    }

    @PostMapping
    public String addAlert(@RequestBody PriceAlert alert) {
        priceService.addAlert(alert);
        return "Price alert added successfully!";
    }

    @GetMapping("/test")
    public void testAlerts(){
        priceAlertScheduler.checkAndNotifyAlerts();
    }

}
