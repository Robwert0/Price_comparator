package com.example.price_comparator.service;

import com.example.price_comparator.model.BasketItems;
import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.util.CsvLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BasketOptimizerService {

    @Autowired
    private CsvLoader csvLoader;

    public Map<String, PriceEntry> optimizeBasket(List<BasketItems> basket, LocalDate date){
        Map<String, PriceEntry> bestDeals = new HashMap<>();

        List<PriceEntry> kauflandPrices = csvLoader.loadPriceEntries("kaufland_2025-05-01.csv", "Kaufland", date);
        System.out.println("Loaded " + kauflandPrices.size() + " Kaufland prices.");

        List<PriceEntry> lidlPrices = csvLoader.loadPriceEntries("lidl_2025-05-01.csv", "Lidl", date);
        System.out.println("Loaded " + lidlPrices.size() + " Lidl prices.");

        List<PriceEntry> profiPrices = csvLoader.loadPriceEntries("profi_2025-05-01.csv", "Profi", date);
        System.out.println("Loaded " + profiPrices.size() + " Profi prices.");

        List<PriceEntry> allPrices = new ArrayList<>();
        allPrices.addAll(kauflandPrices);
        allPrices.addAll(lidlPrices);
        allPrices.addAll(profiPrices);
        System.out.println("Total prices from all stores: " + allPrices.size());

        for (BasketItems item : basket){
            System.out.println("Searching for best deal for: " + item.getProductName()); // Debug log

            PriceEntry best = allPrices.stream()
                    .filter((p -> p.getProduct().getName().toLowerCase().contains(item.getProductName().toLowerCase())))
                    .min(Comparator.comparing(PriceEntry::getPrice))
                    .orElse(null);

            if (best != null){
                bestDeals.put(item.getProductName(), best);
            }
            else {
                System.out.println("No best deal found for: " + item.getProductName()); // Debug log
            }
        }

        return bestDeals;
    }
}
