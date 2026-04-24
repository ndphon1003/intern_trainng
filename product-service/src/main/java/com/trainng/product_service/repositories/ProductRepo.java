package com.trainng.product_service.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.trainng.product_service.models.Product;

@Repository
public interface ProductRepo extends  MongoRepository<Product, UUID>{
    List<Product> findByIsPublicTrueAndIsDeletedFalse();
    
}
