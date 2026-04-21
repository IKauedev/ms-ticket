package com.ms.ticket.infra.http.controller;

import com.ms.ticket.application.dto.CreateTicketRequest;
import com.ms.ticket.application.dto.TicketResponse;
import com.ms.ticket.domain.service.TicketService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BOX_OFFICE')")
    public List<TicketResponse> listTickets() {
        return ticketService.listTickets();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BOX_OFFICE')")
    public TicketResponse getTicket(@PathVariable UUID id) {
        return ticketService.getTicket(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BOX_OFFICE')")
    public ResponseEntity<TicketResponse> createTicket(
            @Valid @RequestBody CreateTicketRequest request,
            UriComponentsBuilder uriComponentsBuilder) {
        TicketResponse response = ticketService.createTicket(request);
        URI location = uriComponentsBuilder.path("/api/tickets/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }
}