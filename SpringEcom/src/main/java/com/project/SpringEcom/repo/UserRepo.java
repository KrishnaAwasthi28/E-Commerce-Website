package com.project.SpringEcom.repo;

import com.project.SpringEcom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByMobileNumber(String mobileNumber);
}
