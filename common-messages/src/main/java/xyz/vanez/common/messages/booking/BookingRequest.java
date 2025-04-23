package xyz.vanez.common.messages.booking;

import java.time.LocalDate;

public record BookingRequest(
        String bookingId,
        String clientId,
        String tourId,
        LocalDate bookingDate
) {}
