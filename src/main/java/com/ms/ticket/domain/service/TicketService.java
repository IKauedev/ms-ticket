package com.ms.ticket.domain.service;

import java.util.List;
import java.util.UUID;

import com.ms.ticket.domain.entity.Ticket;
import com.ms.ticket.infra.repository.TicketRepository;
import com.ms.ticket.application.dto.CreateTicketRequest;
import com.ms.ticket.application.dto.TicketResponse;
import com.ms.ticket.domain.constants.TicketStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> listTickets() {
        return ticketRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(TicketResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TicketResponse getTicket(UUID id) {
        return TicketResponse.from(findTicket(id));
    }

    @Transactional
    public TicketResponse createTicket(CreateTicketRequest request) {
        String normalizedScreeningId = request.screeningId().trim().toUpperCase();
        String normalizedSeatNumber = request.seatNumber().trim().toUpperCase();

        if (ticketRepository.existsByScreeningIdAndSeatNumberIgnoreCase(normalizedScreeningId, normalizedSeatNumber)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seat already sold for this screening");
        }

        Ticket ticket = new Ticket(
                normalizedScreeningId,
                request.movieName().trim(),
                request.sessionTime(),
                normalizedSeatNumber,
                request.customerName().trim(),
                request.price(),
                request.status() == null ? TicketStatus.PURCHASED : request.status());

        return TicketResponse.from(ticketRepository.save(ticket));
    }

    private Ticket findTicket(UUID id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));
    }
}