package com.example.demo;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping("/")
    public String home() {
        return "ms-ticket is running. Time: " + LocalDateTime.now();
    }

    @GetMapping("/health")
    public String health() {
        return "ms-ticket is healthy!";
    }

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return "Hello " + name + "! Welcome to ms-ticket.";
    }
}