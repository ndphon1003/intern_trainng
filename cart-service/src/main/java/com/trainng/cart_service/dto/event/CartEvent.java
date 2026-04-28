package com.trainng.cart_service.dto.event;

public class CartEvent {
    private String productId;
    private int quantity;
    private String userId;
    private String eventType;

    public CartEvent() {
    }

    public CartEvent(String productId, int quantity, String userId, String eventType) {
        this.productId = productId;
        this.quantity = quantity;
        this.userId = userId;
        this.eventType = eventType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}