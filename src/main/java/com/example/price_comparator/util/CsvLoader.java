package com.example.price_comparator.util;

import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.model.Product;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    public List<PriceEntry> loadHistoricalPrices(String productName, String store, String brand, String category){
        List<PriceEntry> allEntries = new ArrayList<>();

        try{
            File folder = new File(getClass().getClassLoader().getResource("data").toURI());
            File[] files = folder.listFiles();

            if (files ==  null){
                log.warn("No CSV files found in /data");
                return allEntries;
            }

            Pattern filePattern = Pattern.compile("^(lidl|kaufland)_\\d{4}-\\d{2}-\\d{2}\\.csv$");

            for (File file: files){
                String fileName = file.getName();

                if (!filePattern.matcher(fileName).matches()) continue;

                String storeName = fileName.startsWith("lidl") ? "Lidl" : "Kaufland";
                String dateStr = fileName.replaceAll(".*_(\\d{4}-\\d{2}-\\d{2})\\.csv", "$1");
                LocalDate date = LocalDate.parse(dateStr);

                List<PriceEntry> entries = loadPriceEntries(fileName, storeName, date);
                for (PriceEntry entry :entries){
                    boolean matches = entry.getProduct().getName().equalsIgnoreCase(productName);
                    if (store != null) matches &= entry.getStore().equalsIgnoreCase(store);
                    if (brand != null) matches &= entry.getProduct().getBrand().equalsIgnoreCase(brand);
                    if (category != null) matches &= entry.getProduct().getCategory().equalsIgnoreCase(category);

                    if (matches) {
                        allEntries.add(entry);
                    }
                }
            }
        }catch (Exception e){
            log.error("Error loading historical prices {}", e.getMessage());
        }

        return allEntries;
    }
}
