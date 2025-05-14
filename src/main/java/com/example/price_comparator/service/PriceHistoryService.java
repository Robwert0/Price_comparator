package com.example.price_comparator.service;

import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.util.CsvLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PriceHistoryService {

    @Autowired
    private CsvLoader csvLoader;

    public List<PriceEntry> getPriceHistory(String productName, String store, String brand, String categoy){
        List<PriceEntry> history = csvLoader.loadHistoricalPrices(productName, store, brand, categoy);
        System.out.println("Requested price history for product: " + productName);
        System.out.println("Matched entries: " + history.size());
        for (PriceEntry entry : history) {
            System.out.println(entry.getDate() + " | " + entry.getPrice() + " | " + entry.getStore());
        }
        history.sort(Comparator.comparing(PriceEntry::getDate));

        return  history;

    }
}
