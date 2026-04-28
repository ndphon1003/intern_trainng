package com.trainng.cart_service.dto.response;

import com.trainng.cart_service.models.Cart;

public class CartResponse {
    private Cart cart;

    public CartResponse() {
    }

    public CartResponse(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}