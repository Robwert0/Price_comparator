package com.example.price_comparator.util;

import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.model.Product;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CsvLoader {

    public List<PriceEntry> loadPriceEntries(String fileName, String storeName, LocalDate date) {
        List<PriceEntry> entries = new ArrayList<>();

        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("data/" + fileName);
            if (is == null) {
                log.warn("File not found: {}", fileName);
                return entries;
            }

            CSVReader reader = new CSVReader(new InputStreamReader(is));
            String[] line;

            // Skip header
            reader.readNext();

            while ((line = reader.readNext()) != null) {
                String name = line[0];
                String brand = line[1];
                String category = line[2];
                double price = Double.parseDouble(line[3]);
                String unit = line[4];
                double grammage = Double.parseDouble(line[5]);


                Product product = new Product(name, brand, category, grammage, unit);
                PriceEntry entry = new PriceEntry(storeName, date, product, price);
                entries.add(entry);
            }

        } catch (Exception e) {
            log.error("Error reading CSV file: {}", e.getMessage());
        }

        return entries;
    }
}
