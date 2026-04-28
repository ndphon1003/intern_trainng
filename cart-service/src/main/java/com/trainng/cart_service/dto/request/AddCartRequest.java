package com.trainng.cart_service.dto.request;

public class AddCartRequest {

    private String productId;
    private int quantity;

    public AddCartRequest() {
    }

    public AddCartRequest(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
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
}