package com.app.clean_Code;

import java.math.BigDecimal;

public class ReceiptResponse {

    private final String orderId;

    private final String userId;

    private final String itemId;

    private final int quantity;

    private final BigDecimal totalAmount;

    public ReceiptResponse(String orderId, String userId, String itemId, int quantity, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }


    @Override
    public String toString() {
        return "OK:" + orderId + ":" + userId + ":" + itemId + ":" + quantity + ":" + totalAmount;
    }
}
