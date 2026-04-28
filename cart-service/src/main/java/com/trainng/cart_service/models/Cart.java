package com.trainng.cart_service.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "carts")
public class Cart {

    @Id
    private UUID id;

    private UUID userId;
    private UUID productId;

    private int quantity;
    private LocalDateTime addAt;

    public Cart() {
        this.addAt = LocalDateTime.now();
        this.id = UUID.randomUUID();
    }

    public Cart(UUID id, UUID userId, UUID productId, int quantity, LocalDateTime addAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.addAt = addAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public LocalDateTime getAddAt() {
        return addAt;
    }

    public void setAddAt(LocalDateTime addAt) {
        this.addAt = addAt;
    }
}