package com.project.SpringEcom.repo;

import com.project.SpringEcom.model.Cart;
import com.project.SpringEcom.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepo extends JpaRepository<CartItem,Integer> {
    List<CartItem> findByCart(Cart cart);
    CartItem findByCartAndProductId(Cart cart, int productId);
}
