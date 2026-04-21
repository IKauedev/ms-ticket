package com.ms.ticket.infra.repository;

import java.util.List;
import java.util.UUID;

import com.ms.ticket.domain.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    boolean existsByScreeningIdAndSeatNumberIgnoreCase(String screeningId, String seatNumber);

    List<Ticket> findAllByOrderByCreatedAtDesc();
}