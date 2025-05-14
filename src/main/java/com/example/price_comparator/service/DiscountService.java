package com.example.price_comparator.service;

import com.example.price_comparator.model.DiscountedProduct;
import com.example.price_comparator.util.CsvLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
public class DiscountService {

    @Autowired
    private CsvLoader csvLoader;

    public List<DiscountedProduct> getBestCurrentDiscounts(LocalDate today){
        List<DiscountedProduct> allDiscounts = new ArrayList<>();
        try{
            File folder = new File(getClass().getClassLoader().getResource("data").toURI());
            File[] files = folder.listFiles();

            if (files == null){
                log.warn("No CSV found in /data");
                return allDiscounts;
            }

            Pattern discountsPattern = Pattern.compile("^(lidl|kaufland|profi)_discounts_\\d{4}-\\d{2}-\\d{2}\\.csv$");

            for (File file : files){
                String fileName = file.getName();

                if (!discountsPattern.matcher(fileName).matches()) continue;

                String storeName = fileName.split("_")[0];

                List<DiscountedProduct> storeDiscounts = csvLoader.loadDiscountedProducts(fileName, storeName, today);
                allDiscounts.addAll(storeDiscounts);
            }
        }catch (Exception e){
            log.warn("Error loading discount files {}", e.getMessage());
        }

        // Sort items by ID
        allDiscounts.sort(Comparator.comparing(dp -> dp.getProduct().getId()));

        return allDiscounts;
    }

}
