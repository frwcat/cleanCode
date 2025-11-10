package com.app.clean_Code;

public class OrderParser {

    public CheckoutRequest parse(String raw, boolean vip) {
        System.out.println("raw : "+raw+", vip : "+vip);

        if(raw == null || raw.trim().isEmpty()) {
            throw new ValidationException("EMPTY_INPUT");
        }

        String[] parts = raw.split(",");

        if(parts.length < 3) {
            throw new ValidationException("INVALID_FORMAT");
        }

        String userId = parts[0].trim();
        String itemId = parts[1].trim();
        String qtyStr = parts[2].trim();
        String coupon = (parts.length >= 4) ? parts[3].trim() : "";

        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
        } catch (NumberFormatException e) {
            throw new ValidationException("QTY_NOT_NUMBER");
        }

        return new CheckoutRequest(userId, itemId, qty, vip, coupon);
    }
}
