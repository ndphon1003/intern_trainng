package com.trainng.product_service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainng.product_service.dto.request.CreateProductRequest;
import com.trainng.product_service.dto.response.ListProductResponse;
import com.trainng.product_service.models.Product;
import com.trainng.product_service.repositories.ProductRepo;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public Product createProduct(UUID userId, CreateProductRequest request){
        Product createProduct = new Product();
        createProduct.setName(request.getName());
        createProduct.setDescription(request.getDescription());
        createProduct.setPrice(request.getPrice());
        createProduct.setStockQuantity(request.getStockQuantity());
        createProduct.setOwnerId(userId);

        Product savedProduct = productRepo.save(createProduct);
        return savedProduct;
    }

    public ListProductResponse getListOfProductPublic(){
        List<Product> products = productRepo.findByIsPublicTrueAndIsDeletedFalse();

        return new ListProductResponse(products.size(), products);
    }

    public ListProductResponse getListOfProductOwn(UUID ownerId){
        List<Product> products = productRepo.findByOwnerIdAndIsDeletedFalse(ownerId);

        return new ListProductResponse(products.size(), products);
    }

    public ListProductResponse getAllProducts(){
        List<Product> products = productRepo.findAll();

        return new ListProductResponse(products.size(), products);
    }

    public Product getDetailProductPublicById(UUID productId){
        Product product = productRepo.findByProductId(productId);
        if (!product.isPublic() || product.isDeleted()){
            return null;
        }
        return product;
    }

    public Product getDetailProductOwnById(UUID productId, UUID ownerId){
        Product product = productRepo.findByProductId(productId);

        if (!ownerId.equals(product.getOwnerId())){
            return null;
        }
        return product;
    }

    public Product getDetailProduct(UUID productId){
        Product product = productRepo.findByProductId(productId);

        return product;
    }
}
