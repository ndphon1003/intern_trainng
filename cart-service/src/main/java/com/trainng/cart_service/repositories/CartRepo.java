package com.trainng.cart_service.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.trainng.cart_service.models.Cart;

@Repository
public interface CartRepo extends MongoRepository<Cart, UUID>{
    
}
