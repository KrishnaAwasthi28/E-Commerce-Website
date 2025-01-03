package com.project.SpringEcom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("hello/{name}")
    public String greet(@PathVariable("name") String name){
        return "Welcome to Project "+name;
    }
}
