package com.project.SpringEcom.controller;

import com.project.SpringEcom.model.User;
import com.project.SpringEcom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:5173/",allowCredentials = "true")
@RequestMapping("/api/auth/")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("register")
    public User register(@RequestBody User user){
        return service.saveUser(user);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user){
        String mobileNumber = user.getMobileNumber();
        String password = user.getPassword();
        User dbUser=service.getUserByMobileNumber(mobileNumber);
//        boolean success = service.login(mobileNumber, password);
//        if (success) {
//            return ResponseEntity.ok(user);
//        } else {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user or password");
//        }
        if (dbUser != null) {
            // Don’t return password for security reasons
//            dbUser.setPassword(null);
            return ResponseEntity.ok(dbUser);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user or password");
        }
    }

}
