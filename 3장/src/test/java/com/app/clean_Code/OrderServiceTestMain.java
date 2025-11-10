package com.app.clean_Code;

public class OrderServiceTestMain {
    public static void main(String[] args) {
        OrderService service = new OrderService();

        System.out.println("=== 정상 케이스 ===");
        try {
            CheckoutRequest req = service.parseRequest("vip001 , A-100 , 2 , COUPON10", true);
            ReceiptResponse receipt = service.checkout(req);
            System.out.println("RECEIPT : " + receipt);
        } catch (RuntimeException e) {
            System.out.println("ERROR : " + e.getMessage());
        }

        System.out.println("\n=== 검증 실패 케이스 ===");
        try {
            CheckoutRequest badReq = service.parseRequest(" ,A-100,-1,", false);
            ReceiptResponse badReceipt = service.checkout(badReq);
            System.out.println("RECEIPT : " + badReceipt);
        } catch (ValidationException e) {
            System.out.println("VALIDATION ERROR : " + e.getMessage());
        }

        System.out.println("\n=== 재고 부족 케이스 ===");
        try {
            CheckoutRequest stockReq = new CheckoutRequest("u1", "B-100", 99, false, "");
            ReceiptResponse stockReceipt = service.checkout(stockReq);
            System.out.println("RECEIPT : " + stockReceipt);
        } catch (OutOfStockException e) {
            System.out.println("OUT OF STOCK : " + e.getMessage());
        }
    }
}
