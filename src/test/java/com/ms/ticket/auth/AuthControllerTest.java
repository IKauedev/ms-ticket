package com.ms.ticket.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.ticket.application.dto.RegisterRequest;
import com.ms.ticket.application.dto.UserProfileResponse;
import com.ms.ticket.domain.constants.UserRole;
import com.ms.ticket.domain.service.AuthService;
import com.ms.ticket.infra.http.controller.AuthController;
import com.ms.ticket.core.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
@TestPropertySource(properties = "app.security.jwt.secret=ms-ticket-test-secret-2026-security-key")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void shouldRegisterCustomer() throws Exception {
        RegisterRequest request = new RegisterRequest("customer1", "Customer One", "customer12345");
        UserProfileResponse response = new UserProfileResponse("customer1", "Customer One", UserRole.CUSTOMER);

        given(authService.register(any(RegisterRequest.class))).willReturn(response);

        mockMvc.perform(post("/api/auth/register")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("customer1"))
                .andExpect(jsonPath("$.role").value("CUSTOMER"));
    }

    // Teste de login removido: login agora é feito via Keycloak/OIDC

    @Test
    @WithMockUser(username = "customer1", roles = "CUSTOMER")
    void shouldReturnCurrentUser() throws Exception {
        UserProfileResponse response = new UserProfileResponse("customer1", "customer1", UserRole.CUSTOMER);

        given(authService.currentUser(any())).willReturn(response);

        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("customer1"))
                .andExpect(jsonPath("$.role").value("CUSTOMER"));
    }
}