package com.example.demo.ticket;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record CreateTicketRequest(
        @NotBlank(message = "screeningId is required") String screeningId,
        @NotBlank(message = "movieName is required") String movieName,
        @NotNull(message = "sessionTime is required") @FutureOrPresent(message = "sessionTime must be in the present or future") OffsetDateTime sessionTime,
        @NotBlank(message = "seatNumber is required") String seatNumber,
        @NotBlank(message = "customerName is required") String customerName,
        @NotNull(message = "price is required") @DecimalMin(value = "0.01", message = "price must be greater than zero") BigDecimal price,
        TicketStatus status) {
}