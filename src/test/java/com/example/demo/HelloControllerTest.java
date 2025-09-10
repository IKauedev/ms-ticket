package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
class HelloControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldReturnHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Hello from Spring Boot")));
    }
    
    @Test
    void shouldReturnHealthStatus() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Application is healthy!"));
    }
    
    @Test
    void shouldReturnPersonalizedGreeting() throws Exception {
        mockMvc.perform(get("/hello/John"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello John! Welcome to CI/CD Pipeline Demo!"));
    }
}