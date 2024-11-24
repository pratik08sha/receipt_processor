package com.fetch.receiptProcessor.repository;

import com.fetch.receiptProcessor.model.Receipt;
import com.fetch.receiptProcessor.service.ReceiptService;

public interface ReceiptRepository  {
    String saveReceipt(Receipt receipt);
    Receipt getReceiptById(String id);

    void savePoints(String id, int points);

    Integer getPointsById(String id);
}
