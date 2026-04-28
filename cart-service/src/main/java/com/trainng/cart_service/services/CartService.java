package com.trainng.cart_service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainng.cart_service.dto.event.CartEvent;
import com.trainng.cart_service.models.Cart;
import com.trainng.cart_service.repositories.CartRepo;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartEventProducerService cartEventProducerService;

    public Cart addProductToCart(UUID userId, UUID productId, int quantity){
        if (quantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than 0"); 
        }
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setQuantity(quantity);

        Cart savedCart = cartRepo.save(cart);

        CartEvent event = new CartEvent();
        event.setEventType("ADD_CART");
        event.setProductId(productId.toString());
        event.setUserId(userId.toString());
        event.setQuantity(quantity);

        System.out.println(event.getEventType());

        cartEventProducerService.sendCartEvent(event);
        return savedCart;
    }

    public List<Cart> getListOfCarts(UUID userId){
        List<Cart> carts = cartRepo.findAll();

        return carts;
    }
}
