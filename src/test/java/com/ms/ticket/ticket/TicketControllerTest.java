package com.ms.ticket.ticket;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ms.ticket.application.dto.CreateTicketRequest;
import com.ms.ticket.application.dto.TicketResponse;
import com.ms.ticket.domain.constants.TicketStatus;
import com.ms.ticket.domain.service.TicketService;
import com.ms.ticket.infra.http.controller.TicketController;
import com.ms.ticket.core.security.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

@WebMvcTest(TicketController.class)
@Import(SecurityConfig.class)
@TestPropertySource(properties = "app.security.jwt.secret=ms-ticket-test-secret-2026-security-key")
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TicketService ticketService;

    @Test
    @WithMockUser(username = "boxoffice", roles = "BOX_OFFICE")
    void shouldCreateTicket() throws Exception {
        UUID ticketId = UUID.fromString("5f6c2c89-1f1f-4db3-83d5-9784341b7c8d");
        OffsetDateTime sessionTime = OffsetDateTime.parse("2030-04-18T20:00:00Z");
        OffsetDateTime now = OffsetDateTime.parse("2030-04-18T18:00:00Z");
        CreateTicketRequest request = new CreateTicketRequest(
                "screening-001",
                "Interstellar",
                sessionTime,
                "A10",
                "Kaue",
                new BigDecimal("32.50"),
                TicketStatus.PURCHASED);
        TicketResponse response = new TicketResponse(
                ticketId,
                "SCREENING-001",
                "Interstellar",
                sessionTime,
                "A10",
                "Kaue",
                new BigDecimal("32.50"),
                TicketStatus.PURCHASED,
                now,
                now);

        given(ticketService.createTicket(any(CreateTicketRequest.class))).willReturn(response);

        mockMvc.perform(post("/api/tickets")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/tickets/" + ticketId)))
                .andExpect(jsonPath("$.movieName").value("Interstellar"))
                .andExpect(jsonPath("$.screeningId").value("SCREENING-001"))
                .andExpect(jsonPath("$.seatNumber").value("A10"));
    }

    @Test
    @WithMockUser(username = "boxoffice", roles = "BOX_OFFICE")
    void shouldListTickets() throws Exception {
        OffsetDateTime sessionTime = OffsetDateTime.parse("2030-04-18T20:00:00Z");
        OffsetDateTime now = OffsetDateTime.parse("2030-04-18T18:00:00Z");
        TicketResponse response = new TicketResponse(
                UUID.fromString("5f6c2c89-1f1f-4db3-83d5-9784341b7c8d"),
                "SCREENING-001",
                "Interstellar",
                sessionTime,
                "A10",
                "Kaue",
                new BigDecimal("32.50"),
                TicketStatus.PURCHASED,
                now,
                now);

        given(ticketService.listTickets()).willReturn(List.of(response));

        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].movieName").value("Interstellar"))
                .andExpect(jsonPath("$[0].customerName").value("Kaue"));
    }

    @Test
    void shouldRejectAnonymousUser() throws Exception {
        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "customer", roles = "CUSTOMER")
    void shouldRejectCustomerRole() throws Exception {
        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isForbidden());
    }
}