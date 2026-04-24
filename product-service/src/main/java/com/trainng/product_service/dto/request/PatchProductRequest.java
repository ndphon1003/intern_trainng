package com.trainng.product_service.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public class PatchProductRequest {
    private UUID productId;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean isPublic;
    private boolean isDeleted;

    // No-args constructor
    public PatchProductRequest() {
    }

    // All-args constructor
    public PatchProductRequest(UUID productId, 
                                String name,
                               String description,
                               BigDecimal price,
                               boolean isPublic,
                               boolean isDeleted) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isPublic = isPublic;
        this.isDeleted = isDeleted;
    }

    // Getter & Setter

    public UUID getProductId(){
        return productId;
    }

    public void setProductId(UUID productId){
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
}