package com.app.clean_Code;

public class CheckoutRequest {
    private final String userId;
    private final String itemId;
    private final int quantity;
    private final boolean vip;
    private final String couponCode;

    public CheckoutRequest(String userId, String itemId, int quantity, boolean vip, String couponCode) {
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.vip = vip;
        this.couponCode = couponCode;
    }

    public String getUserId() {
        return userId;
    }

    public String getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isVip() {
        return vip;
    }

    public String getCouponCode() {
        return couponCode;
    }

    @Override
    public String toString() {
        return "CheckoutRequest{" +
                "userId='" + userId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", quantity=" + quantity +
                ", vip=" + vip +
                ", couponCode='" + couponCode + '\'' +
                '}';
    }
}
