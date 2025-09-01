package com.project.SpringEcom.controller;

import com.project.SpringEcom.model.Cart;
import com.project.SpringEcom.model.CartItem;
import com.project.SpringEcom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173",allowedHeaders = "*")
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public Cart getCartByUser(@PathVariable int userId) {
        return cartService.getCartByUserId(userId);
    }

    // ✅ Get cart items
    @GetMapping("/{cartId}/items")
    public List<CartItem> getCartItems(@PathVariable int cartId) {
        return cartService.getCartItems(cartId);
    }

    // ✅ Add product to cart
    @PostMapping("/{userId}/add")
    public Cart addToCart(@PathVariable int userId,
                          @RequestParam int productId,
                          @RequestParam int quantity) {
        return cartService.addProductToCart(userId, productId, quantity);
    }

    // ✅ Update item quantity
    @PutMapping("/item/{cartItemId}")
    public void updateCartItem(@PathVariable int cartItemId,
                               @RequestParam int quantity) {
        cartService.updateCartItem(cartItemId, quantity);
    }

    // ✅ Remove item
    @DeleteMapping("/item/{cartItemId}")
    public void removeItem(@PathVariable int cartItemId) {
        cartService.removeCartItem(cartItemId);
    }

    // ✅ Clear cart
    @DeleteMapping("/{cartId}/clear")
    public void clearCart(@PathVariable int cartId) {
        cartService.clearCart(cartId);
    }
}
