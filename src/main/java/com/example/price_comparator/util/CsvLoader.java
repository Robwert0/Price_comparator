package com.example.price_comparator.util;

import com.example.price_comparator.model.DiscountedProduct;
import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.model.Product;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
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

            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is)).withCSVParser(parser).build();
            String[] line;

            // Skip header
            reader.readNext();

            while ((line = reader.readNext()) != null) {
                String id = line[0];
                String name = line[1];
                String category = line[2];
                String brand = line[3];
                double quantity = Double.parseDouble(line[4]);
                String unit = line[5];
                double price = Double.parseDouble(line[6]);
                String currency = line[7];

                Product product = new Product(id, name, category, brand, quantity, unit);
                PriceEntry entry = new PriceEntry(storeName, date, product, price, currency);
                entries.add(entry);
            }

        } catch (Exception e) {
            log.error("Error reading CSV file: {}", e.getMessage());
        }

        return entries;
    }

    public List<DiscountedProduct> loadDiscountedProducts(String fileName, String storeName, LocalDate today){
        List<DiscountedProduct> discountedProducts = new ArrayList<>();

        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("data/" + fileName);
            if (is == null) {
                log.warn("Discount file not found: {}", fileName);
                return discountedProducts;
            }

            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is)).withCSVParser(parser).build();

            String[] line;
            reader.readNext();

            while ((line = reader.readNext()) != null) {
                String id = line[0];
                String name = line[1];
                String brand = line[2];
                double quantity = Double.parseDouble(line[3]);
                String unit = line[4];
                String category = line[5];
                LocalDate startDate = LocalDate.parse(line[6]);
                LocalDate endDate = LocalDate.parse(line[7]);
                int percentage = Integer.parseInt(line[8]);


                // Only include discounts valid today
                if (today.isBefore(startDate) || today.isAfter(endDate)) {
                    continue;
                }

                Product product = new Product(id, name, category, brand, quantity, unit);
                DiscountedProduct discountedProduct = new DiscountedProduct(product, storeName, startDate, endDate, percentage);

                discountedProducts.add(discountedProduct);
            }

        } catch (Exception e) {
            log.error("Error reading discount CSV '{}': {}", fileName, e.getMessage());
        }

        return discountedProducts;
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

            // Regex pattern to match files like lidl_2025-05-05.csv or kaufland_2025-05-05.csv
            Pattern filePattern = Pattern.compile("^(lidl|kaufland|profi)_\\d{4}-\\d{2}-\\d{2}\\.csv$");

            for (File file: files){
                String fileName = file.getName();

                //Skip files that don't match the naming pattern
                if (!filePattern.matcher(fileName).matches()) continue;

                String storeName = fileName.startsWith("lidl") ? "Lidl" : fileName.startsWith("Kaufland") ? "Kaufland" : "Profi";

                //Extract date from file name
                String dateStr = fileName.replaceAll(".*_(\\d{4}-\\d{2}-\\d{2})\\.csv", "$1");
                LocalDate date = LocalDate.parse(dateStr);

                List<PriceEntry> entries = loadPriceEntries(fileName, storeName, date);
                for (PriceEntry entry :entries){
                    // Match product name (required), and optionally store, brand, and category
                    boolean matches = entry.getProduct().getName().toLowerCase().contains(productName.toLowerCase());
                    if (store != null) matches &= entry.getStore().toLowerCase().contains(store.toLowerCase());
                    if (brand != null) matches &= entry.getProduct().getBrand().toLowerCase().contains(brand.toLowerCase());
                    if (category != null) matches &= entry.getProduct().getCategory().toLowerCase().contains(category.toLowerCase());

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
