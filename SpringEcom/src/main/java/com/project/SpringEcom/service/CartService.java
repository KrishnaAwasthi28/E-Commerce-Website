package com.project.SpringEcom.service;

import com.project.SpringEcom.model.Cart;
import com.project.SpringEcom.model.CartItem;
import com.project.SpringEcom.repo.CartItemRepo;
import com.project.SpringEcom.repo.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    public Cart getCartByUserId(int userId) {
        return cartRepo.findByUserId(userId);
    }

    public Cart addProductToCart(int userId, int productId, int quantity) {
        Cart cart = cartRepo.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart = cartRepo.save(cart);
        }

        CartItem existingItem = cartItemRepo.findByCartAndProductId(cart, productId);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepo.save(existingItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            cartItemRepo.save(cartItem);
        }

        return cart;
    }

    public List<CartItem> getCartItems(int cartId) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return cartItemRepo.findByCart(cart);
    }

    public void updateCartItem(int cartItemId, int quantity) {
        CartItem item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (quantity <= 0) {
            cartItemRepo.delete(item); // remove item if quantity 0
        } else {
            item.setQuantity(quantity);
            cartItemRepo.save(item);
        }
    }

    public void removeCartItem(int cartItemId) {
        cartItemRepo.deleteById(cartItemId);
    }

    public void clearCart(int cartId) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cartItemRepo.deleteAll(cart.getCartItems());
    }
}
