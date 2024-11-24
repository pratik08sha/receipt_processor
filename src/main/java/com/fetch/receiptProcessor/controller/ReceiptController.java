package com.fetch.receiptProcessor.controller;

import com.fetch.receiptProcessor.exceptions.ApiException;
import com.fetch.receiptProcessor.model.PointsResponse;
import com.fetch.receiptProcessor.model.ProcessResponse;
import com.fetch.receiptProcessor.model.Receipt;
import com.fetch.receiptProcessor.model.ReceiptPoints;
import com.fetch.receiptProcessor.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<ProcessResponse> processReceipt(@RequestBody Receipt receipt) {
        ProcessResponse response = receiptService.processReceipt(receipt);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/{id}/points")
    public ResponseEntity<PointsResponse> getPointsById(@PathVariable String id) {
            if (id == null || id.isEmpty()) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Id cannot be null.");
            }
        PointsResponse response = receiptService.getPointsById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
