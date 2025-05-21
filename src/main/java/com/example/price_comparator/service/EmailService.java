package com.example.price_comparator.service;

import com.example.price_comparator.model.PriceAlert;
import com.example.price_comparator.model.PriceEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendPriceAlert(String to, String subject, String message){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }

    public void sendAlertEmail(PriceAlert alert, List<PriceEntry> matchingEntries) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Your price alert for: ").append(alert.getProductName()).append("\n");
        messageBuilder.append("Target Price: ").append(alert.getTargetPrice()).append("\n\n");

        for (PriceEntry entry : matchingEntries) {
            messageBuilder.append("Store: ").append(entry.getStore()).append("\n");
            messageBuilder.append("Price: ").append(entry.getPrice()).append("\n");
            messageBuilder.append("Product: ").append(entry.getProduct().getName()).append("\n");
            messageBuilder.append("Date: ").append(entry.getDate()).append("\n\n");
        }

        sendPriceAlert(alert.getUserEmail(), "Price Drop Alert: " + alert.getProductName(), messageBuilder.toString());
    }
}
