package com.example.price_comparator.service;

import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.util.CsvLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final CsvLoader csvLoader;

    public List<PriceEntry> getAllPrices() {
        List<PriceEntry> allEntries = new ArrayList<>();

        allEntries.addAll(csvLoader.loadPriceEntries("lidl_2025-05-08.csv", "Lidl", LocalDate.of(2025, 5, 8)));
        allEntries.addAll(csvLoader.loadPriceEntries("kaufland_2025-05-08.csv", "Kaufland", LocalDate.of(2025, 5, 8)));

        return allEntries;
    }
}
