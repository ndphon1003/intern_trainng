package com.trainng.cart_service.dto.request;

import java.util.UUID;

public class UpdateQuantityRequest {
    private UUID productId;
    private int quantity;

    public UpdateQuantityRequest() {
    }

    public UpdateQuantityRequest(UUID productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}