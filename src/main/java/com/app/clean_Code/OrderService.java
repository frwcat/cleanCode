package com.app.clean_Code;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderService {

    private static final int UNIT_PRICE_A_TYPE = 100;

    private static final int UNIT_PRICE_DEFAULT = 150;

    private static final int SHIPPING_FEE_A_TYPE = 500;

    private static final int SHIPPING_FEE_DEFAULT = 1200;

    private static final int UNIT_WEIGHT_KG  = 2;

    private static final int EXTRA_WEIGHT_LIMIT_KG  = 10;

    private static final int EXTRA_WEIGHT_FEE = 800;

    private static final int MAX_ORDER_QUANTITY = 20;

    public CheckoutRequest parseRequest(String raw, boolean vip) {
        OrderParser parser = new OrderParser();
        return parser.parse(raw, vip);
    }

    public ReceiptResponse checkout(CheckoutRequest req) {
        System.out.println("[CHECKOUT START] " + req);
        validate(req);
        checkStock(req);

        BigDecimal itemPrice = calculateItemPrice(req);
        BigDecimal shippingFee = calculateShippingFee(req);
        BigDecimal total = itemPrice.add(shippingFee);

        total = applyDiscount(total, req);

        String orderId = saveOrder(req, total);

        ReceiptResponse receipt = buildReceipt(orderId, req, total);

        System.out.println("[CHECKOUT END] " + receipt);
        return receipt;
    }

    //재고 여부를 확인
    private void checkStock(CheckoutRequest req) {
        if(req.getQuantity() > MAX_ORDER_QUANTITY) {
            throw new OutOfStockException("chkReq.getQuantity=" + req.getQuantity());
        }
    }

    //상품 금액(단가 * 수량)을 계산
    private BigDecimal calculateItemPrice(CheckoutRequest req) {
        int unitPrice = req.getItemId().startsWith("A") ? UNIT_PRICE_A_TYPE : UNIT_PRICE_DEFAULT;
        BigDecimal pricePerUnit = BigDecimal.valueOf(unitPrice);
        BigDecimal quantity = BigDecimal.valueOf(req.getQuantity());
        BigDecimal totalItemPrice = pricePerUnit.multiply(quantity);
        return totalItemPrice;
    }

    //배송비를 계산 (기본 배송비 + 무게에 따른 추가요금)
    private BigDecimal calculateShippingFee(CheckoutRequest req) {
        int base = req.getItemId().startsWith("A") ? SHIPPING_FEE_A_TYPE : SHIPPING_FEE_DEFAULT;
        int weight = UNIT_WEIGHT_KG * req.getQuantity();
        int extra = (weight > EXTRA_WEIGHT_LIMIT_KG) ? EXTRA_WEIGHT_FEE : 0;
        BigDecimal shippingFee = BigDecimal.valueOf(base + extra);
        return shippingFee;
    }

    //VIP, 쿠폰 정보를 바탕으로 할인 금액 적용
    private BigDecimal applyDiscount(BigDecimal initPrice, CheckoutRequest req) {
        BigDecimal result = initPrice;

        if(req.isVip()) {
            result = result.multiply(BigDecimal.valueOf(0.9));
        }

        if("COUPON10".equalsIgnoreCase(req.getCouponCode())) {
            result = result.subtract(BigDecimal.TEN);
        }

        return result;
    }

    //입력 값 검증
    public void validate(CheckoutRequest req) {
        if (req.getUserId() == null || req.getUserId().trim().isEmpty()) {
            throw new ValidationException("USER_REQUIRED");
        }

        if (req.getItemId() == null || req.getItemId().trim().isEmpty()) {
            throw new ValidationException("ITEM_REQUIRED");
        }

        if (req.getQuantity() <= 0) {
            throw new ValidationException("QTY_POSITIVE");
        }
    }

    //주문 정보를 저장
    public String saveOrder(CheckoutRequest req, BigDecimal total) {
        String orderId = UUID.randomUUID().toString();
        System.out.println("saveOrder : order=" + orderId + " user=" + req.getUserId() + " item=" + req.getItemId() + " qty=" + req.getQuantity() + " sum=" + total);
        return orderId;
    }

    //영수증 객체
    public ReceiptResponse buildReceipt(String orderId, CheckoutRequest req, BigDecimal total) {
        return new ReceiptResponse(orderId, req.getUserId(), req.getItemId(), req.getQuantity(), total);
    }
}
