package com.trainng.cart_service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trainng.cart_service.dto.event.CartEvent;
import com.trainng.cart_service.dto.request.UpdateQuantityRequest;
import com.trainng.cart_service.dto.response.ResponseFormat;
import com.trainng.cart_service.models.Cart;
import com.trainng.cart_service.repositories.CartRepo;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartEventProducerService cartEventProducerService;
    @Autowired
    RestTemplate restTemplate;

    public Cart addProductToCart(UUID userId, UUID productId, int quantity){
        if (quantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than 0"); 
        }

        UpdateQuantityRequest requestBody = new UpdateQuantityRequest(productId, quantity);
        String url = "http://localhost:8083/api/product/update-quantity";
        


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UpdateQuantityRequest> requesHttpEntity = new HttpEntity<>(requestBody, headers);
        
        ResponseEntity<ResponseFormat> response = 
            restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requesHttpEntity,
                ResponseFormat.class
            );

        ResponseFormat body = response.getBody();
        System.out.println(body.getData());
        if (!response.getStatusCode().is2xxSuccessful() || body == null) {
            throw new RuntimeException("Quantity update failed");
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

        cartEventProducerService.sendCartEvent(event, "add-cart-event");
        return savedCart;
    }

    public void removeProductFromCart(UUID cartId, UUID userId){
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (!cart.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: user does not own this cart");
        }

        CartEvent event = new CartEvent();
        event.setEventType("REMOVE_CART");
        event.setProductId(cart.getProductId().toString());
        event.setUserId(cart.getUserId().toString());
        event.setQuantity(cart.getQuantity());

        cartRepo.deleteById(cartId);
        cartEventProducerService.sendCartEvent(event, "remove-cart-event");
    }

    public List<Cart> getListOfCarts(UUID userId){
        List<Cart> carts = cartRepo.findByUserId(userId);

        return carts;
    }
}
