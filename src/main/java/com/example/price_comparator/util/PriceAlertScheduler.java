package com.example.price_comparator.util;

import com.example.price_comparator.model.PriceAlert;
import com.example.price_comparator.model.PriceEntry;
import com.example.price_comparator.model.Product;
import com.example.price_comparator.service.EmailService;
import com.example.price_comparator.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceAlertScheduler {
    private final PriceService priceService;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 8 * * *")
    public void checkAndNotifyAlerts(){
        log.info("Running scheduled price alert check ... ");

        List<PriceAlert> alerts = priceService.getAlerts();

        log.info("All registered alerts:");
        for (PriceAlert a : alerts) {
            log.info(" -> {}", a);
        }

        for (PriceAlert alert : alerts){
            List<PriceEntry> matchedEntries = priceService.checkAlerts(alert);

            if (!matchedEntries.isEmpty()){
                log.info("Price match found for alert: {}", alert);
                emailService.sendAlertEmail(alert, matchedEntries);
            }else{
                log.info("No price drop found for alert: {}", alert.getProductName());
            }
        }
    }
}
