package com.fetch.receiptProcessor.service;

import com.fetch.receiptProcessor.model.PointsResponse;
import com.fetch.receiptProcessor.model.ProcessResponse;
import com.fetch.receiptProcessor.model.Receipt;
import com.fetch.receiptProcessor.model.ReceiptPoints;

public interface ReceiptService {
    ProcessResponse processReceipt(Receipt receipt);
    PointsResponse getPointsById(String id);
}
