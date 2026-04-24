package com.trainng.product_service.dto.response;

import java.util.List;

import com.trainng.product_service.models.Product;

public class ListProductResponse {

    private int totalProduct;
    private List<Product> listProduct;

    // No-args constructor
    public ListProductResponse() {
    }

    // All-args constructor
    public ListProductResponse(int totalProduct, List<Product> listProduct) {
        this.totalProduct = totalProduct;
        this.listProduct = listProduct;
    }

    // Getter & Setter

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }
}