package com.fetch.receiptProcessor.service.impl;
import com.fetch.receiptProcessor.model.PointsResponse;
import com.fetch.receiptProcessor.model.ProcessResponse;
import com.fetch.receiptProcessor.model.Receipt;
import com.fetch.receiptProcessor.model.ReceiptPoints;
import com.fetch.receiptProcessor.repository.ReceiptRepository;
import com.fetch.receiptProcessor.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.math.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    @Autowired
    public ReceiptServiceImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }
    @Override
    public ProcessResponse processReceipt(Receipt receipt) {
        if (receipt == null) {
            throw new IllegalArgumentException("Receipt cannot be null");
        }

        if (receipt.getRetailer() == null || receipt.getRetailer().isEmpty()) {
            throw new IllegalArgumentException("Retailer cannot be empty");
        }

        if (receipt.getItems() == null || receipt.getItems().isEmpty()) {
            throw new IllegalArgumentException("Receipt must have at least one item");
        }
        String id = receiptRepository.saveReceipt(receipt);
        executorService.submit(() -> {
            int points = calculatePoints(receipt);
            receiptRepository.savePoints(id, points);
        });
        return new ProcessResponse(id);
    }

    @Override
    public PointsResponse getPointsById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid receipt ID");
        }

        Integer points = receiptRepository.getPointsById(id);
        if (points == null) {
            throw new IllegalArgumentException("Receipt not found for ID: " + id);
        }

        return new PointsResponse(points);
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;

        // Rule 1: One point for every alphanumeric character in the retailer name
        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        // Rule 2: 50 points if the total is a round dollar amount with no cents
        BigDecimal total = new BigDecimal(receipt.getTotal());
        if (total.stripTrailingZeros().scale() == 0) {
            points += 50;
        }

        // Rule 3: 25 points if the total is a multiple of 0.25
        if (total.remainder(new BigDecimal("0.25")).compareTo(BigDecimal.ZERO) == 0) {
            points += 25;
        }

        // Rule 4: 5 points for every two items on the receipt
        points += (receipt.getItems().size() / 2) * 5;

        // Rule 5: If the trimmed length of the item description is a multiple of 3,
        // multiply the price by 0.2, round up, and add to points
        for (var item : receipt.getItems()) {
            String trimmedDescription = item.getShortDescription().trim();
            if (trimmedDescription.length() % 3 == 0) {
                BigDecimal price = new BigDecimal(item.getPrice());
                points += price.multiply(new BigDecimal("0.2")).setScale(0, BigDecimal.ROUND_UP).intValue();
            }
        }

        // Rule 6: 6 points if the day in the purchase date is odd
        LocalDate purchaseDate = LocalDate.parse(receipt.getPurchaseDate());
        if (purchaseDate.getDayOfMonth() % 2 != 0) {
            points += 6;
        }

        // Rule 7: 10 points if the time of purchase is after 2:00 PM and before 4:00 PM
        LocalTime purchaseTime = LocalTime.parse(receipt.getPurchaseTime());
        if (purchaseTime.isAfter(LocalTime.of(14, 0)) && purchaseTime.isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }

        return points;
    }
}
