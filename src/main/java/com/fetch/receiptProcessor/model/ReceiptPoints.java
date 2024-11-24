package com.fetch.receiptProcessor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptPoints {
    private String id;
    private Integer points;
    public ReceiptPoints(String id) {
        this.id = id;
    }
}
