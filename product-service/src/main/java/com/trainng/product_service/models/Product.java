package com.trainng.product_service.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product")
public class Product {

    @Id
    private UUID productId;

    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private boolean isPublic;
    private boolean isDeleted;

    @Indexed
    private UUID ownerId;

    private int currentVersion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //No-args constructor
// No-args constructor
    public Product() {
        this.productId = UUID.randomUUID();      
        this.price = BigDecimal.ZERO;            
        this.stockQuantity = 0;
        this.isPublic = true;                    
        this.isDeleted = false;                   
        this.currentVersion = 1;           
        this.createdAt = LocalDateTime.now();   
        this.updatedAt = LocalDateTime.now();
    }

    // All-args constructor
    public Product(UUID product_id, String name, String description, BigDecimal price,
                   int stockQuantity, boolean isPublic, boolean isDeleted,
                   UUID ownerId, int currentVersion,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = product_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.isPublic = isPublic;
        this.isDeleted = isDeleted;
        this.ownerId = ownerId;
        this.currentVersion = currentVersion;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    //Getters & Setters

    public UUID getProduct_id() {
        return productId;
    }

    public void setProduct_id(UUID productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public int getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(int currentVersion) {
        this.currentVersion = currentVersion;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}