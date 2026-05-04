package com.trainng.cart_service.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainng.cart_service.dto.request.AddCartRequest;
import com.trainng.cart_service.dto.response.ResponseFormat;
import com.trainng.cart_service.models.Cart;
import com.trainng.cart_service.services.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    
    @PostMapping("/add")
    public ResponseEntity<ResponseFormat> addCartRoute(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody AddCartRequest request) {

        try {
            UUID userUUID = UUID.fromString(userId);
            UUID productUUID = UUID.fromString(request.getProductId());

            Cart cart = cartService.addProductToCart(
                    userUUID,
                    productUUID,
                    request.getQuantity()
            );

            return ResponseEntity.ok(
                    new ResponseFormat(HttpStatus.OK.value(), "Add product to cart successfully", cart)
            );

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseFormat(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseFormat> getListOfCarts(
            @RequestHeader("X-User-Id") String userId){
        
        try {
            UUID userUUID = UUID.fromString(userId);
            
            List<Cart> carts = cartService.getListOfCarts(userUUID);

            return ResponseEntity.ok(
                new ResponseFormat(HttpStatus.OK.value(), "Get list of carts successully", carts)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null));
        }
    }

    @DeleteMapping("/delete")
    
    public ResponseEntity<ResponseFormat> removeCartItem(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam("cartId") String cartId
    ) {
        try {
            UUID userUUID = UUID.fromString(userId);
            UUID cartUUID = UUID.fromString(cartId);

            cartService.removeProductFromCart(cartUUID, userUUID);

            return ResponseEntity.ok(
                    new ResponseFormat(HttpStatus.OK.value(), "Remove cart item successfully", null)
            );

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseFormat(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseFormat(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null));
        }
    }
}
