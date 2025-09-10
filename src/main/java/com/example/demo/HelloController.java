package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
public class HelloController {
    
    @GetMapping("/")
    public String home() {
        return "Hello from Spring Boot CI/CD Demo! Time: " + LocalDateTime.now();
    }
    
    @GetMapping("/health")
    public String health() {
        return "Application is healthy!";
    }
    
    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return "Hello " + name + "! Welcome to CI/CD Pipeline Demo!";
    }
}