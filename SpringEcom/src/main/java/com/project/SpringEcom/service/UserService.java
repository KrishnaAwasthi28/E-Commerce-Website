package com.project.SpringEcom.service;

import com.project.SpringEcom.model.User;
import com.project.SpringEcom.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public User saveUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getMobileNumber() == null) {
            throw new IllegalArgumentException("Mobile number cannot be null");
        }
//        System.out.println(user.getPassword());
        return repo.save(user);
    }

    public boolean login(String mobileNumber, String password) {
        User user = repo.findByMobileNumber(mobileNumber);
        if (user != null && encoder.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }

    public User getUserByMobileNumber(String mobileNumber) {
        User user=repo.findByMobileNumber(mobileNumber);
        return user;
    }
}
