package com.example.price_comparator.service;

import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.util.CsvLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewDiscountService {

    @Autowired
    private CsvLoader csvLoader;

    public List<PriceEntry> getNewDiscounts(LocalDate today, LocalDate yesterday){
        List<PriceEntry> todayPrices = new ArrayList<>();
        List<PriceEntry> yesterdayPrices = new ArrayList<>();

        todayPrices.addAll(csvLoader.loadPriceEntries("lidl_2025-05-08.csv", "Lidl", today));
        todayPrices.addAll(csvLoader.loadPriceEntries("kaufland_2025-05-08.csv", "Kaufland", today));
        System.out.println("✅ Today's prices loaded: " + todayPrices.size());

        yesterdayPrices.addAll(csvLoader.loadPriceEntries("lidl_2025-05-07.csv", "Lidl", yesterday));
        yesterdayPrices.addAll(csvLoader.loadPriceEntries("kaufland_2025-05-07.csv", "Kaufland", yesterday));
        System.out.println("✅ Yesterday's prices loaded: " + yesterdayPrices.size());

        Map<String, Double> yesterdayPricMap = new HashMap<>();
        for (PriceEntry entry : yesterdayPrices){
            String key = entry.getStore() + " " + entry.getProduct().getName().toLowerCase();
            yesterdayPricMap.put(key, entry.getPrice());
        }
        System.out.println(yesterdayPricMap);

        List<PriceEntry> newDiscounts = new ArrayList<>();
        for (PriceEntry todayEntry : todayPrices){
            String key = todayEntry.getStore() + " " + todayEntry.getProduct().getName().toLowerCase();
            Double oldPrice = yesterdayPricMap.get(key);

            if (oldPrice != null && todayEntry.getPrice() < oldPrice){
                newDiscounts.add(todayEntry);
                System.out.println("Discount found: " + key + " from " + oldPrice + " → " + todayEntry.getPrice());
            } else if (oldPrice != null) {
                System.out.println("No discount: " + key + " stayed at " + oldPrice + " or increased to " + todayEntry.getPrice());
            } else {
                System.out.println("No matching old price for key: " + key);
            }
        }
        return newDiscounts;
    }
}
