package com.example.price_comparator.service;

import com.example.price_comparator.model.DiscountedProduct;
import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.util.CsvLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.event.ListDataEvent;
import java.security.PublicKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {

    @Autowired
    private CsvLoader csvLoader;

    public List<DiscountedProduct> getBestDiscounts(LocalDate date){
        // Today list of prices
        List<PriceEntry> todayLidl = csvLoader.loadPriceEntries("lidl_2025-05-08.csv", "Lidl", date);
        List<PriceEntry> todayKaufland = csvLoader.loadPriceEntries("kaufland_2025-05-08.csv", "Kaufland", date);

        // Prices of 3 days ago
        List<PriceEntry> oldLidl = csvLoader.loadPriceEntries("lidl_2025-05-05.csv", "Lidl", date.minusDays(3));
        List<PriceEntry> oldKaufland = csvLoader.loadPriceEntries("kaufland_2025-05-05.csv", "Kaufland", date.minusDays(3));

        List<PriceEntry> currentEntries = new ArrayList<>();
        currentEntries.addAll(todayLidl);
        currentEntries.addAll(todayKaufland);

        List<PriceEntry> oldEntries = new ArrayList<>();
        oldEntries.addAll(oldLidl);
        oldEntries.addAll(oldKaufland);

        List<DiscountedProduct> bestDiscounts = new ArrayList<>();

        for (PriceEntry current : currentEntries){
            Optional<PriceEntry> oldMatch = oldEntries.stream()
                    .filter(e -> e.getProduct().equals(current.getProduct()) &&
                                    e.getStore().equals(current.getStore()))
                    .findFirst();

            if (oldMatch.isPresent()){
                double oldPrice = oldMatch.get().getPrice();
                double newPrice = current.getPrice();

                if (newPrice < oldPrice){
                    double discount = ((oldPrice-newPrice) / oldPrice) * 100;
                    bestDiscounts.add(new DiscountedProduct(current.getProduct(), current.getStore(), oldPrice, newPrice, discount));

                }
            }
        }
        bestDiscounts.sort((a, b) -> Double.compare(b.getDiscountedPercentage(), a.getDiscountedPercentage()));

        return bestDiscounts;

    }
}
