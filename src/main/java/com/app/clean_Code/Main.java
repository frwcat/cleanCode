package com.app.clean_Code;

public class Main {
    public static void main(String[] args){
        OrderService service = new OrderService();

        String raw = "vip001 , A-100 , 2 , COUPON10";
        boolean vip = true;

        try {
            CheckoutRequest req = service.parseRequest(raw, vip);
            ReceiptResponse receipt = service.checkout(req);

            System.out.println("RECEIPT : " + receipt);
        } catch (ValidationException e) {
            System.out.println("VALIDATION ERROR : " + e.getMessage());
        } catch (OutOfStockException e) {
            System.out.println("OUT OF STOCK : " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("UNEXPECTED ERROR : " + e.getMessage());
        }
    }
}
