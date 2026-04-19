package com.example.demo.ticket;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record TicketResponse(
        UUID id,
        String screeningId,
        String movieName,
        OffsetDateTime sessionTime,
        String seatNumber,
        String customerName,
        BigDecimal price,
        TicketStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {

    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getScreeningId(),
                ticket.getMovieName(),
                ticket.getSessionTime(),
                ticket.getSeatNumber(),
                ticket.getCustomerName(),
                ticket.getPrice(),
                ticket.getStatus(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt());
    }
}