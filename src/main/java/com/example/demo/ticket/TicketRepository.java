package com.example.demo.ticket;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    boolean existsByScreeningIdAndSeatNumberIgnoreCase(String screeningId, String seatNumber);

    List<Ticket> findAllByOrderByCreatedAtDesc();
}