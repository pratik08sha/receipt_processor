package com.fetch.receiptProcessor.repository.impl;

import com.fetch.receiptProcessor.model.Receipt;
import com.fetch.receiptProcessor.repository.ReceiptRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ReceiptRepositoryImpl implements ReceiptRepository {
    private final Map<String, Receipt> receiptStorage = new ConcurrentHashMap<>();
    private final Map<String, Integer> pointsStorage = new ConcurrentHashMap<>();

    @Override
    public Integer getPointsById(String id) {
        return pointsStorage.get(id);
    }

    @Override
    public void savePoints(String id, int points) {
        pointsStorage.put(id, points);
    }

    @Override
    public String saveReceipt(Receipt receipt) {
        String id = UUID.randomUUID().toString();
        receiptStorage.put(id, receipt);
        return id;
    }

    @Override
    public Receipt getReceiptById(String id) {

        return receiptStorage.get(id);
    }
}
