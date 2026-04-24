package com.trainng.product_service.controllers;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainng.product_service.dto.request.CreateProductRequest;
import com.trainng.product_service.dto.response.ListProductResponse;
import com.trainng.product_service.dto.response.ResponseFormat;
import com.trainng.product_service.models.Product;
import com.trainng.product_service.services.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ResponseFormat> createProduct(@RequestHeader("X-User-Id") UUID userId, 
            @RequestBody CreateProductRequest request){
        
        if (request.getStockQuantity() <= 0 || request.getPrice().compareTo(BigDecimal.ZERO) <= 0 || request.getPrice() == null){
            return ResponseEntity.badRequest().body(new ResponseFormat(HttpStatus.BAD_REQUEST.value(), "Stock or Price must be more than 0", null));
        }

        try {
            Product product = productService.createProduct(userId, request);
            return ResponseEntity.ok(new ResponseFormat(HttpStatus.OK.value(), "Created Product successfully", product));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseFormat(HttpStatus.BAD_REQUEST.value(), "Failed to upload product", null));
        }
    }

    @GetMapping("/list")
    ResponseEntity<ResponseFormat> getListProductPublic(){
        try {
            ListProductResponse response = productService.getListOfProductPublic();
            return ResponseEntity.ok(new ResponseFormat(HttpStatus.OK.value(), "Get list of product successfully", response));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(new ResponseFormat(HttpStatus.BAD_REQUEST.value(), "Failed to load products", null));
        }
    }
}
