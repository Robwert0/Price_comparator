package com.example.price_comparator.controller;

import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendation")
public class RecommendationController {

    private final PriceService priceService;

    @GetMapping("/value")
    public ResponseEntity<List<PriceEntry>> getBestValueRecommendation(
            @RequestParam String productName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){

        if (date ==null){
            date = LocalDate.now();
        }

        return ResponseEntity.ok(priceService.getBestValueAlternative(productName, date));
    }

    @GetMapping("/best-buy")
    public ResponseEntity<PriceEntry> getBestBuy(
            @RequestParam String productName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){

        if (date ==  null){
            date = LocalDate.now();
        }

        return ResponseEntity.ok(priceService.getBestBuy(productName, date));
    }
}
