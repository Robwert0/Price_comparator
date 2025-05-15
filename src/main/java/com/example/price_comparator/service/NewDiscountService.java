package com.example.price_comparator.service;

import com.example.price_comparator.model.DiscountedProduct;
import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.util.CsvLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Service
public class NewDiscountService {

    @Autowired
    private CsvLoader csvLoader;

    public List<DiscountedProduct> getNewDiscounts(LocalDate today) {
        List<DiscountedProduct> newDiscounts = new ArrayList<>();

        try {
            File folder = new File(getClass().getClassLoader().getResource("data").toURI());
            File[] files = folder.listFiles();

            if (files == null) {
                log.warn("No csv files found in /data");
                return newDiscounts;
            }

            Pattern discountsPattern = Pattern.compile("^(lidl|kaufland|profi)_discounts_\\d{4}-\\d{2}-\\d{2}\\.csv$");

            for (File file : files) {
                String fileName = file.getName();
                if (!discountsPattern.matcher(fileName).matches()) continue;

                String storeName = fileName.split("_")[0];

                List<DiscountedProduct> discounts = csvLoader.loadDiscountedProducts(fileName, storeName, today);

                for (DiscountedProduct d : discounts) {
                    // Add discount only if its start date (fromDate) is within the last 24 hours
                    if (!d.getFromDate().isBefore(today.minusDays(1)) && !d.getFromDate().isAfter(today)) {
                        newDiscounts.add(d);
                    }
                }
            }

        } catch (Exception e) {
            log.warn("Error loading new discounts {}", e.getMessage());
        }
        newDiscounts.sort(Comparator.comparing(DiscountedProduct::getFromDate).reversed());

        return newDiscounts;
    }
}
