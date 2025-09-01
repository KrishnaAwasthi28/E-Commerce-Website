package com.project.SpringEcom.repo;

import com.project.SpringEcom.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart,Integer> {
    Cart findByUserId(int userId);
}
